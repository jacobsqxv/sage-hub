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

@Slf4j
@Component
@RequiredArgsConstructor
public class Checks {
	private final UserUtil userUtil;
	private final PasswordEncoder passwordEncoder;

	public <E extends Enum<E>> void checkIfEnumExists(Class<E> enumClass, String request) {
		try {
			Enum.valueOf(enumClass, request.toUpperCase());
		}
		catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException(String.format(ExceptionConstants.NOT_FOUND,
					enumClass.getSimpleName()));
		}
	}

	public User currentlyLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return this.userUtil.getUser(username);
	}

	public void isAdminOrLoggedIn(Long id) {
		if (!(checkAdmin() || currentlyLoggedInUser().getId().equals(id))) {
			log.info("INFO - Unauthorized access to this resource");
			throw new UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}

	public void isAdmin() {
		RoleEnum loggedInUserRole = currentlyLoggedInUser().getRole().getName();
		if (!(loggedInUserRole.equals(RoleEnum.ADMIN) ||
				loggedInUserRole.equals(RoleEnum.SUPER_ADMIN))) {
			log.info("INFO - Unauthorized access");
			throw new UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}

	public void isCurrentlyLoggedInUser(Long id) {
		User user = currentlyLoggedInUser();
		if (!user.getId().equals(id)) {
			log.info("INFO - Unauthorized access to information for this user");
			throw new UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}

	public boolean isPasswordEqual(User user, String password) {
		return this.passwordEncoder.matches(password, user.getHashedPassword());
	}

	public boolean checkAdmin() {
		RoleEnum loggedInUserRole = currentlyLoggedInUser().getRole().getName();
		return loggedInUserRole.equals(RoleEnum.ADMIN) || loggedInUserRole.equals(RoleEnum.SUPER_ADMIN);
	}
}
