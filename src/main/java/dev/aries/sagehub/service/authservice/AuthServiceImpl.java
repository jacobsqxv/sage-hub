package dev.aries.sagehub.service.authservice;

import java.time.LocalDateTime;
import java.util.Objects;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.constant.ResponseMessage;
import dev.aries.sagehub.dto.request.AuthRequest;
import dev.aries.sagehub.dto.request.ResetPassword;
import dev.aries.sagehub.dto.request.ResetPasswordRequest;
import dev.aries.sagehub.dto.response.AuthResponse;
import dev.aries.sagehub.dto.response.AuthToken;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.enums.TokenType;
import dev.aries.sagehub.mapper.UserMapper;
import dev.aries.sagehub.model.Token;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.TokenRepository;
import dev.aries.sagehub.repository.UserRepository;
import dev.aries.sagehub.security.TokenService;
import dev.aries.sagehub.service.emailservice.EmailService;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.GlobalUtil;
import dev.aries.sagehub.util.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;

/**
 * AdminServiceImpl is a service class that implements the AdminService interface.
 * It provides methods for managing admins, including adding a new admin.
 * @author Jacobs Agyei
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
	private final DaoAuthenticationProvider daoAuthProvider;
	@Qualifier("jwtRefreshTokenAuthProvider")
	private final JwtAuthenticationProvider jwtAuthProvider;
	private final TokenService tokenService;
	private final GlobalUtil globalUtil;
	private final UserUtil userUtil;
	private final EmailService emailService;
	private final Generators generator;
	private final Checks checks;
	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	private static final Integer STATUS_OK = HttpStatus.OK.value();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthResponse authenticateUser(AuthRequest request) {
		Integer countOfFailedAttempts;
		Authentication authentication;
		User user = this.userUtil.getUser(request.username().value());
		checkLockedAccount(user);
		try {
		authentication = this.daoAuthProvider
			.authenticate(new UsernamePasswordAuthenticationToken(
					request.username().value(), request.password()));
		user = this.userUtil.getUser(authentication.getName());
		}
		catch (BadCredentialsException ex) {
			countOfFailedAttempts = user.getFailedLoginAttempts() + 1;
			user.setFailedLoginAttempts(countOfFailedAttempts);
			updateLockTime(user, countOfFailedAttempts);
			this.userRepository.save(user);
			log.info("INFO - Number of failed login attempts: {}", countOfFailedAttempts);
			throw new BadCredentialsException(ExceptionConstants.INVALID_CREDENTIALS);
		}
		AuthToken authToken = this.tokenService.generateToken(Objects.requireNonNull(authentication));
		String accessToken = authToken.accessToken();
		String refreshToken = authToken.refreshToken();
		user.setLockTime(null);
		user.setFailedLoginAttempts(0);
		user.setLastLogin(LocalDateTime.now());
		this.userRepository.save(user);
		return new AuthResponse(
				accessToken,
				refreshToken,
				this.userMapper.toAuthUserResponse(user)
				);
	}

	@Override
	public AuthResponse renewRefreshToken(String token) {
		try {
			Authentication authentication = this.jwtAuthProvider
					.authenticate(new BearerTokenAuthenticationToken(token));
			User user = this.userUtil.getUser(authentication.getName());
			AuthToken authToken = this.tokenService.generateToken(authentication);
			this.tokenService.updateRefreshToken(user.getId(), token, authToken.refreshToken());
			return new AuthResponse(
					authToken.accessToken(),
					authToken.refreshToken(),
					this.userMapper.toAuthUserResponse(user)
			);
		}
		catch (AuthenticationException ex) {
			log.info("INFO - Error refreshing token: {}", ex.getMessage());
			throw new IllegalArgumentException(ExceptionConstants.AUTHENTICATION_FAILED);
		}
	}

	private void checkLockedAccount(User user) {
		if (user.getLockTime() != null && user.getLockTime().isAfter(LocalDateTime.now())) {
			String time = this.globalUtil.formatDateTime(user.getLockTime());
			throw new LockedException(String.format(ExceptionConstants.ACCOUNT_LOCKED, time));
		}
	}

	private void updateLockTime(User user, Integer failedAttempts) {
		if (failedAttempts >= 5) {
			user.setLockTime(LocalDateTime.now().plusMinutes(15));
			user.setFailedLoginAttempts(0);
		}
	}

	@Override
	public GenericResponse resetPasswordRequest(ResetPasswordRequest request) {
		User user = this.userUtil.getUser(request.username());
		String value = this.generator.generateToken(16);
		Token token = Token.builder()
				.value(value)
				.type(TokenType.RESET_PASSWORD)
				.userId(user.getId())
				.expiresAt(LocalDateTime.now().plusMinutes(15))
				.build();
		this.tokenRepository.save(token);
		String recipient = getRecipient(user.getId());
		this.emailService.sendPasswordResetEmail(recipient, value);
		log.info("INFO - Reset password token: {}", value);
		return new GenericResponse(STATUS_OK, String.format(ResponseMessage.EMAIL_SENT, "Password reset"));
	}

	@Override
	public GenericResponse resetPassword(String token, ResetPassword request) {
		log.info("INFO - User requesting password reset: {}", request.username());
		User user = this.userUtil.getUser(request.username());
		validateToken(token, user.getId(), TokenType.RESET_PASSWORD);
		if (this.checks.isPasswordEqual(user, request.password())) {
			throw new IllegalArgumentException(ExceptionConstants.INVALID_CURRENT_PASSWORD);
		}
		user.setHashedPassword(this.passwordEncoder.encode(request.password()));
		this.userRepository.save(user);
		String recipient = getRecipient(user.getId());
		this.emailService.sendPasswordResetCompleteEmail(recipient, "login");
		return new GenericResponse(STATUS_OK, ResponseMessage.PASSWORD_RESET_SUCCESS);
	}

	private void validateToken(String value, Long userId, TokenType type) {
		Token token = this.tokenRepository.findByValueAndUserIdAndType(value, userId, type);
		if (token == null) {
			throw new EntityNotFoundException(String.format(ExceptionConstants.NOT_FOUND, "Token"));
		}
		if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException(ExceptionConstants.EXPIRED_TOKEN);
		}
	}

	private String getRecipient(Long userId) {
		User user = this.userUtil.getUser(userId);
		if (this.checks.isAdmin(user.getRole().getName())) {
			return this.userUtil.getBasicInfo(userId).primaryEmail();
		}
		return this.userUtil.getBasicInfo(userId).secondaryEmail();
	}
}
