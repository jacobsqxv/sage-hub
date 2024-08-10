package dev.aries.sagehub.service.authservice;

import java.time.LocalDateTime;
import java.util.Objects;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.constant.ResponseMessage;
import dev.aries.sagehub.dto.request.AuthRequest;
import dev.aries.sagehub.dto.request.RefreshTokenRequest;
import dev.aries.sagehub.dto.request.ResetPassword;
import dev.aries.sagehub.dto.request.ResetPasswordRequest;
import dev.aries.sagehub.dto.request.VoucherRequest;
import dev.aries.sagehub.dto.response.AuthResponse;
import dev.aries.sagehub.dto.response.AuthToken;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.enums.TokenStatus;
import dev.aries.sagehub.enums.TokenType;
import dev.aries.sagehub.factory.ModelFactory;
import dev.aries.sagehub.mapper.UserMapper;
import dev.aries.sagehub.model.Token;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.repository.TokenRepository;
import dev.aries.sagehub.repository.UserRepository;
import dev.aries.sagehub.security.TokenService;
import dev.aries.sagehub.service.emailservice.EmailService;
import dev.aries.sagehub.service.voucherservice.VoucherService;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.GlobalUtil;
import dev.aries.sagehub.util.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
 * Implementation of the {@code AuthService} interface.
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.authservice.AuthService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
	private final DaoAuthenticationProvider daoAuthProvider;
	private final JwtAuthenticationProvider jwtAuthProvider;
	private final PasswordEncoder passwordEncoder;
	private final TokenRepository tokenRepository;
	private final VoucherService voucherService;
	private final UserRepository userRepository;
	private final EmailService emailService;
	private final TokenService tokenService;
	private final ModelFactory modelFactory;
	private final UserUtil userUtil;
	private final Checks checks;
	private static final Integer STATUS_OK = HttpStatus.OK.value();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthResponse authenticateUser(AuthRequest request) {
		Integer countOfFailedAttempts;
		Authentication authentication;
		User user = userUtil.getUser(request.username());
		checkLockedAccount(user);
		try {
		authentication = daoAuthProvider
			.authenticate(new UsernamePasswordAuthenticationToken(
					request.username().value(), request.password().value()));
		}
		catch (BadCredentialsException ex) {
			countOfFailedAttempts = user.getFailedLoginAttempts() + 1;
			user.setFailedLoginAttempts(countOfFailedAttempts);
			updateLockTime(user, countOfFailedAttempts);
			userRepository.save(user);
			log.info("Number of failed login attempts: {}", countOfFailedAttempts);
			throw new BadCredentialsException(ExceptionConstants.INVALID_CREDENTIALS);
		}
		AuthToken authToken = tokenService.generateToken(Objects.requireNonNull(authentication),
				user, request.rememberMe());
		String accessToken = authToken.accessToken();
		String refreshToken = authToken.refreshToken();
		user.setLockTime(null);
		user.setFailedLoginAttempts(0);
		user.setLastLogin(LocalDateTime.now());
		userRepository.save(user);
		return new AuthResponse(
				accessToken,
				refreshToken,
				UserMapper.toAuthUserResponse(user)
				);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthResponse loginWithVoucher(VoucherRequest request) {
		Username username = new Username(String.valueOf(request.serialNumber()));
		Password password = new Password(request.pin());
		if (!userUtil.userExists(username)) {
			voucherService.verifyVoucher(request);
			User user = modelFactory.createNewUser(
					username,
					password, RoleEnum.APPLICANT);
			userRepository.save(user);
			log.info("New user added:: username: {}", username.value());
		}
		AuthRequest authRequest = new AuthRequest(username, password);
		return authenticateUser(authRequest);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthResponse renewRefreshToken(RefreshTokenRequest request) {
		try {
			Authentication authentication = jwtAuthProvider
					.authenticate(new BearerTokenAuthenticationToken(request.token()));
			User user = userUtil.getUser(new Username(authentication.getName()));
			tokenService.blacklistOldToken(user.getId(), request.token());
			AuthToken authToken = tokenService.generateToken(authentication, user, true);
			return new AuthResponse(
					authToken.accessToken(),
					authToken.refreshToken(),
					UserMapper.toAuthUserResponse(user)
			);
		}
		catch (AuthenticationException ex) {
			log.info("Error refreshing token: {}", ex.getMessage());
			throw new IllegalArgumentException(ExceptionConstants.AUTHENTICATION_FAILED);
		}
	}

	private void checkLockedAccount(User user) {
		if (user.getLockTime() != null && user.getLockTime().isAfter(LocalDateTime.now())) {
			String time = GlobalUtil.formatDateTime(user.getLockTime());
			throw new LockedException(String.format(ExceptionConstants.ACCOUNT_LOCKED, time));
		}
	}

	private void updateLockTime(User user, Integer failedAttempts) {
		if (failedAttempts >= 5) {
			user.setLockTime(LocalDateTime.now().plusMinutes(15));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GenericResponse resetPasswordRequest(ResetPasswordRequest request) {
		User user = userUtil.getUser(request.username());
		String value = Generators.generateToken(16);
		Token token = Token.builder()
				.value(value)
				.type(TokenType.RESET_PASSWORD)
				.userId(user.getId())
				.expiresAt(LocalDateTime.now().plusMinutes(15))
				.status(TokenStatus.ACTIVE)
				.build();
		tokenRepository.save(token);
		Email recipient = userUtil.getRecipient(user);
		emailService.sendPasswordResetEmail(recipient, value);
		log.info("Reset password token: {}", value);
		return new GenericResponse(STATUS_OK, String.format(ResponseMessage.EMAIL_SENT, "Password reset"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GenericResponse resetPassword(String token, ResetPassword request) {
		log.info("User requesting password reset: {}", request.username().value());
		User user = userUtil.getUser(request.username());
		Token passwordResetToken = loadPasswordResetToken(token, user.getId());
		validateToken(passwordResetToken);
		if (checks.isPasswordEqual(user, request.password())) {
			throw new IllegalArgumentException(ExceptionConstants.PASSWORD_ALREADY_USED);
		}
		user.setHashedPassword(passwordEncoder.encode(request.password()));
		userRepository.save(user);
		updateTokenStatus(passwordResetToken);
		Email recipient = userUtil.getRecipient(user);
		emailService.sendPasswordResetCompleteEmail(recipient, "login");
		return new GenericResponse(STATUS_OK, ResponseMessage.PASSWORD_RESET_SUCCESS);
	}

	private Token loadPasswordResetToken(String value, Long userId) {
		return tokenRepository.findByValueAndUserIdAndType(value, userId, TokenType.RESET_PASSWORD);
	}

	private void validateToken(Token token) {
		if (token == null) {
			throw new EntityNotFoundException(String.format(ExceptionConstants.NOT_FOUND, "Token"));
		}
		if (!token.getStatus().equals(TokenStatus.ACTIVE)) {
			throw new IllegalArgumentException(
					String.format(ExceptionConstants.INVALID_TOKEN, "Token"));
		}
	}

	private void updateTokenStatus(Token token) {
		token.setStatus(TokenStatus.USED);
		tokenRepository.save(token);
	}
}
