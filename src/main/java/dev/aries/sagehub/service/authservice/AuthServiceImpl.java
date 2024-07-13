package dev.aries.sagehub.service.authservice;

import java.time.LocalDateTime;
import java.util.Objects;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.constant.ResponseMessage;
import dev.aries.sagehub.dto.request.AuthRequest;
import dev.aries.sagehub.dto.request.ResetPassword;
import dev.aries.sagehub.dto.request.ResetPasswordRequest;
import dev.aries.sagehub.dto.response.AuthResponse;
import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.enums.TokenType;
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

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	private final AuthenticationManager authenticationManager;

	private final TokenService tokenService;
	private final GlobalUtil globalUtil;
	private final UserUtil userUtil;
	private final EmailService emailService;
	private final Generators generator;
	private final Checks checks;
	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private static final Integer STATUS_OK = HttpStatus.OK.value();

	/**
	 * Authenticates a user.
	 * @param request the request containing the username and password.
	 * @return an AuthResponse containing the authentication token and user information.
	 */
	@Override
	public AuthResponse authenticateUser(AuthRequest request) {
		Integer countOfFailedAttempts;
		Authentication authentication;
		User user = this.userUtil.getUser(request.username());
		checkLockedAccount(user);
		try {
		authentication = this.authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(
					request.username(), request.password()));
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
		String token = this.tokenService.generateToken(Objects.requireNonNull(authentication));
		user.setLockTime(null);
		user.setFailedLoginAttempts(0);
		user.setLastLogin(LocalDateTime.now());
		this.userRepository.save(user);
		BasicUserResponse userResponse = this.userUtil.getBasicInfo(
				this.userUtil.getUserInfo(user.getId()));
		return new AuthResponse(
				token,
				userResponse,
				user.getLastLogin(),
				user.getStatus().toString(),
				user.isAccountEnabled());
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
		String recipient = this.userUtil.getPrimaryEmail(user.getId());
		this.emailService.sendPasswordResetEmail(recipient, value);
		log.info("INFO - Reset password token: {}", value);
		return new GenericResponse(STATUS_OK, String.format(ResponseMessage.EMAIL_SENT, "Password reset"));
	}

	@Override
	public GenericResponse resetPassword(String token, ResetPassword request) {
		log.info("INFO - User requesting password reset: {}", request.username());
		User user = this.userUtil.getUser(request.username());
		validateToken(token, user, TokenType.RESET_PASSWORD);
		if (this.checks.isPasswordEqual(user, request.password())) {
			throw new IllegalArgumentException(ExceptionConstants.INVALID_CURRENT_PASSWORD);
		}
		user.setHashedPassword(this.passwordEncoder.encode(request.password()));
		this.userRepository.save(user);
		String recipient = this.userUtil.getPrimaryEmail(user.getId());
		this.emailService.sendPasswordResetCompleteEmail(recipient, "login");
		return new GenericResponse(STATUS_OK, ResponseMessage.PASSWORD_RESET_SUCCESS);
	}

	private void validateToken(String value, User user, TokenType type) {
		Long userId = user.getId();
		Token token = this.tokenRepository.findByValueAndUserIdAndType(value, userId, type);
		if (token == null) {
			throw new EntityNotFoundException(String.format(ExceptionConstants.NOT_FOUND, "Token"));
		}
		if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException(ExceptionConstants.EXPIRED_TOKEN);
		}
	}
}
