package dev.aries.sagehub.service.authservice;

import java.time.LocalDateTime;
import java.util.Objects;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * AdminServiceImpl is a service class that implements the AdminService interface.
 * It provides methods for managing admins, including adding a new admin.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;

	private final TokenService tokenService;
	private final UserUtil userUtil;
	private final UserRepository userRepository;
	private final GlobalUtil globalUtil;
	/**
	 * Authenticates a user.
	 * @param request The request containing the username and password.
	 * @return An AuthResponse containing the authentication token and user information.
	 */
	@Override
	public AuthResponse authenticateUser(AuthRequest request) {
		Integer countOfFailedAttempts;
		Authentication authentication = null;
		User user = this.userUtil.getUser(request.username());
		try {
		authentication = this.authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(
					request.username(), request.password()));
		user = this.userUtil.getUser(authentication.getName());
		log.info("INFO - AuthService: {} authenticated successfully", authentication.getName());
		}
		catch (BadCredentialsException ex) {
			countOfFailedAttempts = user.getFailedLoginAttempts() + 1;
			user.setFailedLoginAttempts(countOfFailedAttempts);
			if (countOfFailedAttempts >= 5) {
				user.setAccountLocked(true);
				user.setFailedLoginAttempts(0);
				log.info("INFO - Account locked for user: {}", user.getUsername());
			}
			this.userRepository.save(user);
			log.info("INFO - {} ", ex.getMessage());
			log.info("INFO - Number of failed login attempts: {}", countOfFailedAttempts);
		}
		String token = this.tokenService.generateToken(Objects.requireNonNull(authentication));
		user.setFailedLoginAttempts(0);
		user.setLastLogin(LocalDateTime.now());
		this.userRepository.save(user);
		BasicUserResponse userResponse = this.globalUtil.getBasicInfo(
				this.globalUtil.getUserInfo(user.getId()));
		return new AuthResponse(
				token,
				userResponse,
				user.getLastLogin(),
				user.getFailedLoginAttempts(),
				user.isAccountLocked(),
				user.isAccountEnabled());
	}

}
