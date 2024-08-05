package dev.aries.sagehub.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.exception.UnauthorizedAccessException;
import dev.aries.sagehub.model.AcademicPeriod;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.repository.ApplicantRepository;
import dev.aries.sagehub.repository.ProgramCourseRepository;
import dev.aries.sagehub.repository.StaffRepository;
import dev.aries.sagehub.repository.StudentRepository;
import jakarta.persistence.EntityExistsException;
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
	private final ProgramCourseRepository programCourseRepository;
	private static final PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();
	private static final String DEFAULT_COUNTRY_CODE = "GH";

	/**
	 * Check if an enum value exists for the string passed into the function.
	 * @param enumClass - The enum class to check against.
	 * @param request - the string to check against the enum.
	 * @param <E> - The enum type.
	 */
	public static <E extends Enum<E>> void checkIfEnumExists(Class<E> enumClass, String request) {
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
		return userUtil.getUser(username);
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
			log.info("Unauthorized access to this resource");
			throw new UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}

	public void checkAdmins(RoleEnum role) {
		if (!(role.equals(RoleEnum.ADMIN) ||
				role.equals(RoleEnum.SUPER_ADMIN))) {
			log.info("Unauthorized access");
			throw new UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}

	public void isCurrentlyLoggedInUser(Long id) {
		User user = currentlyLoggedInUser();
		if (!user.getId().equals(id)) {
			log.info("Unauthorized access to information for this user");
			throw new UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}

	public boolean isPasswordEqual(User user, Password password) {
		return passwordEncoder.matches(password.value(), user.getHashedPassword());
	}

	public static boolean isAdmin(RoleEnum role) {
		return role.equals(RoleEnum.ADMIN) || role.equals(RoleEnum.SUPER_ADMIN);
	}
	public void checkStudentExists(Long studentId) {
		if (studentRepository.existsById(studentId)) {
			throw new EntityExistsException(String.format(ALREADY_EXISTS, "Student"));
		}
	}

	public void checkStaffExists(Long staffId) {
		if (staffRepository.existsById(staffId)) {
			throw new EntityExistsException(String.format(ALREADY_EXISTS, "Staff"));
		}
	}

	public void checkApplicantExists(Long userId) {
		if (applicantRepository.existsByUserId(userId)) {
			throw new EntityExistsException(
					String.format(ALREADY_EXISTS, "Applicant"));
		}
	}

	public void checkProgramCourse(Long programId, Long courseId, AcademicPeriod period) {
		boolean courseExistsAtPeriod = programCourseRepository.existsByProgramIdAndCourseIdAndAcademicPeriod(
				programId, courseId, period);
		boolean courseExists = programCourseRepository.existsByProgramIdAndCourseId(programId, courseId);
		if (courseExistsAtPeriod || courseExists) {
			throw new EntityExistsException(
					String.format(ALREADY_EXISTS, "Course configuration"));
		}
	}

	public static boolean isValidPhoneNumber(String number, String countryCode) {
		if (countryCode == null || countryCode.isEmpty()) {
			countryCode = DEFAULT_COUNTRY_CODE;
		}
		try {
			Phonenumber.PhoneNumber phoneNumber = PHONE_NUMBER_UTIL.parse(number, countryCode);
			return PHONE_NUMBER_UTIL.isValidNumberForRegion(phoneNumber, countryCode);
		}
		catch (NumberParseException ex) {
			return false;
		}
	}
}
