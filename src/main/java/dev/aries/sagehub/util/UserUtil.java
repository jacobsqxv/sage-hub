package dev.aries.sagehub.util;

import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.exception.UnauthorizedAccessException;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import static dev.aries.sagehub.constant.ExceptionConstants.NO_USER_FOUND;
import static dev.aries.sagehub.constant.ExceptionConstants.UNAUTHORIZED_ACCESS;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserUtil {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final RoleUtil roleUtil;

	private final Generators generators;

	public User getUser(String username) {
		return this.userRepository.findByUsername(username)
			.orElseThrow(() -> new EntityNotFoundException(NO_USER_FOUND));
	}

	public User getUser(Long id) {
		return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NO_USER_FOUND));
	}

	public User createNewUser(String firstName, String lastName, RoleEnum roleEnum) {
		String password = this.generators.generatePassword();
		User user = User.builder()
			.username(this.generators.generateUsername(firstName, lastName))
			.hashedPassword(this.passwordEncoder.encode(password))
			.accountEnabled(true)
			.role(this.roleUtil.getRole(roleEnum))
			.failedLoginAttempts(0)
			.status(Status.ACTIVE)
			.build();
		log.info("INFO - Generated password for {} is {}", user.getUsername(), password);
		return this.userRepository.save(user);
	}

	private User currentlyLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return getUser(username);
	}

	public void isAdminOrLoggedIn(Long id) {
		RoleEnum loggedInUserRole = currentlyLoggedInUser().getRole().getName();
		if (!(loggedInUserRole.equals(RoleEnum.ADMIN) ||
				loggedInUserRole.equals(RoleEnum.SUPER_ADMIN)) &&
				!currentlyLoggedInUser().getId().equals(id)) {
			log.info("INFO - Unauthorized access to this resource");
			throw new UnauthorizedAccessException(UNAUTHORIZED_ACCESS);
		}
	}

	public void isAdmin() {
		RoleEnum loggedInUserRole = currentlyLoggedInUser().getRole().getName();
		if (!(loggedInUserRole.equals(RoleEnum.ADMIN) ||
				loggedInUserRole.equals(RoleEnum.SUPER_ADMIN))) {
			log.info("INFO - Unauthorized access");
			throw new UnauthorizedAccessException(UNAUTHORIZED_ACCESS);
		}
	}

	public void isCurrentlyLoggedInUser(Long id) {
		User user = currentlyLoggedInUser();
		if (!user.getUsername().equals(getUser(id).getUsername())) {
			log.info("INFO - Unauthorized access to information for {}", user.getUsername());
			throw new UnauthorizedAccessException(UNAUTHORIZED_ACCESS);
		}
	}

	public boolean isPasswordValid(String password) {
		User user = currentlyLoggedInUser();
		return this.passwordEncoder.matches(password, user.getHashedPassword());
	}

}
