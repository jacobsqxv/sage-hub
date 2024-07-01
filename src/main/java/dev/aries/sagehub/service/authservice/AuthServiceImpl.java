package dev.aries.sagehub.service.authservice;

import java.time.LocalDateTime;
import java.util.Objects;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.AuthRequest;
import dev.aries.sagehub.dto.response.AuthResponse;
import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.UserRepository;
import dev.aries.sagehub.security.TokenService;
import dev.aries.sagehub.util.GlobalUtil;
import dev.aries.sagehub.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
	private final UserRepository userRepository;
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
				user.getStatus().getValue(),
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
}
