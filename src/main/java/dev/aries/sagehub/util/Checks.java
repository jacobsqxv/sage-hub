package dev.aries.sagehub.util;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.exception.UnauthorizedAccessException;
import dev.aries.sagehub.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import static dev.aries.sagehub.constant.ExceptionConstants.UNAUTHORIZED_ACCESS;

@Slf4j
@Component
@RequiredArgsConstructor
public class Checks {
	private final UserUtil userUtil;
	private final PasswordEncoder passwordEncoder;

	public <E extends Enum<E>> void checkIfEnumExists(Class<E> enumClass, String request) {
		try {
			Enum.valueOf(enumClass, request.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(String.format(ExceptionConstants.NOT_FOUND, enumClass.getSimpleName()));
		}
	}

	private User currentlyLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return this.userUtil.getUser(username);
	}

	public void isAdminOrLoggedIn(Long id) {
		isAdmin();
		if (!currentlyLoggedInUser().getId().equals(id)) {
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
		if (!user.getUsername().equals(this.userUtil.getUser(id).getUsername())) {
			log.info("INFO - Unauthorized access to information for {}", user.getUsername());
			throw new UnauthorizedAccessException(UNAUTHORIZED_ACCESS);
		}
	}

	public boolean isPasswordValid(String password) {
		User user = currentlyLoggedInUser();
		return this.passwordEncoder.matches(password, user.getHashedPassword());
	}

	public boolean checkAdmin() {
		RoleEnum loggedInUserRole = currentlyLoggedInUser().getRole().getName();
		return loggedInUserRole.equals(RoleEnum.ADMIN) || loggedInUserRole.equals(RoleEnum.SUPER_ADMIN);
	}
}
