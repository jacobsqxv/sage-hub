package dev.aries.sagehub.util;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.exception.UnauthorizedAccessException;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.repository.ApplicantRepository;
import dev.aries.sagehub.repository.StaffRepository;
import dev.aries.sagehub.repository.StudentRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static dev.aries.sagehub.constant.ExceptionConstants.ALREADY_EXISTS;

@Slf4j
@Component
@RequiredArgsConstructor
public class Checks {
	private final UserUtil userUtil;
	private final PasswordEncoder passwordEncoder;
	private final StaffRepository staffRepository;
	private final StudentRepository studentRepository;
	private final ApplicantRepository applicantRepository;

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
		Username username = new Username(authentication.getName());
		return this.userUtil.getUser(username);
	}

	public void isAdminOrLoggedIn(String username) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authUser = authentication.getName();
		RoleEnum role = authentication.getAuthorities().stream()
				.findFirst()
				.map((authority) -> authority.getAuthority().replace("SCOPE_", ""))
				.map(RoleEnum::valueOf)
				.orElseThrow(() -> new UnauthorizedAccessException(
						ExceptionConstants.UNAUTHORIZED_ACCESS));
		if (!(isAdmin(role) || authUser.equals(username))) {
			log.info("INFO - Unauthorized access to this resource");
			throw new UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}

	public void checkAdmins(RoleEnum role) {
		if (!(role.equals(RoleEnum.ADMIN) ||
				role.equals(RoleEnum.SUPER_ADMIN))) {
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

	public boolean isPasswordEqual(User user, Password password) {
		return this.passwordEncoder.matches(password.value(), user.getHashedPassword());
	}

	public boolean isAdmin(RoleEnum role) {
		return role.equals(RoleEnum.ADMIN) || role.equals(RoleEnum.SUPER_ADMIN);
	}
	public void checkStudentExists(Long studentId) {
		if (this.studentRepository.existsById(studentId)) {
			throw new EntityExistsException(String.format(ALREADY_EXISTS, "Student"));
		}
	}

	public void checkStaffExists(Long staffId) {
		if (this.staffRepository.existsById(staffId)) {
			throw new EntityExistsException(String.format(ALREADY_EXISTS, "Staff"));
		}
	}

	public void checkApplicantExists(Long userId) {
		if (this.applicantRepository.existsByUserId(userId)) {
			throw new EntityExistsException(
					String.format(ALREADY_EXISTS, "Applicant"));
		}
	}
}
