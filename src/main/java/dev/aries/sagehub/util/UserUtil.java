package dev.aries.sagehub.util;

import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
			.orElseThrow(() -> new EntityNotFoundException("User not found"));
	}

	public User getUser(Long id) {
		return this.userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
	}

	public User createNewUser(String firstName, String lastName, RoleEnum roleEnum) {
		String password = this.generators.generatePassword();
		User user = User.builder()
			.username(this.generators.generateUsername(firstName, lastName))
			.hashedPassword(this.passwordEncoder.encode(password))
			.accountEnabled(true)
			.role(this.roleUtil.getRole(roleEnum))
			.failedLoginAttempts(0)
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
			log.warn("WARN - Unauthorized access");
			throw new IllegalStateException("Unauthorized access");
		}
	}

	public void isCurrentlyLoggedInUser(Long id) {
		User user = currentlyLoggedInUser();
		if (!user.getUsername().equals(getUser(id).getUsername())) {
			log.warn("WARN - UserService: Not authorized to access information for {}", user.getUsername());
			throw new IllegalArgumentException("You are not authorized to access this user's information");
		}
	}

	public boolean isPasswordValid(String password) {
		User user = currentlyLoggedInUser();
		return this.passwordEncoder.matches(password, user.getHashedPassword());
	}

}
