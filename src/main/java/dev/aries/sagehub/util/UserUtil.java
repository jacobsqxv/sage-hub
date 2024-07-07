package dev.aries.sagehub.util;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.enums.AccountStatus;
import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.MaritalStatus;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.enums.Title;
import dev.aries.sagehub.model.Admin;
import dev.aries.sagehub.model.ContactInfo;
import dev.aries.sagehub.model.EmergencyContact;
import dev.aries.sagehub.model.Role;
import dev.aries.sagehub.model.Staff;
import dev.aries.sagehub.model.Student;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.AdminRepository;
import dev.aries.sagehub.repository.StaffRepository;
import dev.aries.sagehub.repository.StudentRepository;
import dev.aries.sagehub.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static dev.aries.sagehub.constant.ExceptionConstants.NOT_FOUND;
import static dev.aries.sagehub.constant.ExceptionConstants.NO_INFO_FOUND;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserUtil {

	private static final String CONTACT = "contact";
	private static final String EMERGENCY_CONTACT = "emergency contact";
	private static final String USER = "User";
	private static final String GET_CONTACT_INFO = "getContactInfo";
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleUtil roleUtil;
	private final AdminRepository adminRepository;
	private final StaffRepository staffRepository;
	private final StudentRepository studentRepository;

	public User getUser(String username) {
		log.info("INFO - Getting user with username: {}", username);
		return this.userRepository.findByUsername(username)
				.orElseThrow(
						() -> new EntityNotFoundException(String.format(NOT_FOUND, USER)));
	}

	public User getUser(Long id) {
		log.info("INFO - Getting user with user ID: {}", id);
		return this.userRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format(NOT_FOUND, USER)));
	}

	public User createNewUser(String username, String password, RoleEnum roleEnum) {
		User user = User.builder()
				.username(username)
				.hashedPassword(this.passwordEncoder.encode(password))
				.accountEnabled(true)
				.role(this.roleUtil.getRole(roleEnum))
				.failedLoginAttempts(0)
				.status(AccountStatus.ACTIVE)
				.build();
		return this.userRepository.save(user);
	}

	public Object getUserInfo(Long id) {
		Role userRole = getUser(id).getRole();
		switch (userRole.getName()) {
			case SUPER_ADMIN, ADMIN -> {
				return this.adminRepository.findByUserId(id);
			}
			case STAFF -> {
				return this.staffRepository.findByUserId(id);
			}
			case STUDENT -> {
				return this.studentRepository.findByUserId(id);
			}
			default -> throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
		}
	}

	public BasicUserResponse getBasicInfo(Object userInfo) {
		if (userInfo instanceof Optional<?> optional && optional.isPresent()) {
			Object user = optional.get();
			switch (user) {
				case Admin admin -> {
					return getUserResponse(admin);
				}
				case Staff staff -> {
					return getUserResponse(staff);
				}
				case Student student -> {
					return getUserResponse(student);
				}
				default -> throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
			}
		}
		throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
	}

	public <T> BasicUserResponse getUserResponse(T user) {
		if (user == null) {
			throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
		}
		try {
			User linkedUser = (User) user.getClass().getMethod("getUser").invoke(user);
			BasicUserResponse.BasicUserResponseBuilder response = BasicUserResponse.builder()
					.id(linkedUser.getId())
					.profilePicture((String) user.getClass()
							.getMethod("getProfilePictureUrl").invoke(user))
					.fullname((String) user.getClass().getMethod("fullName").invoke(user))
					.username(linkedUser.getUsername())
					.primaryEmail((String) user.getClass()
							.getMethod("getPrimaryEmail").invoke(user))
					.status(linkedUser.getStatus().toString())
					.role(linkedUser.getRole().getName().name());

			if (user instanceof Student) {
				getStudentResponse(user, response);
			}
			else if (user instanceof Staff) {
				getStaffResponse(user, response);
			}

			return response.build();
		}
		catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
			throw new IllegalStateException(ex.getMessage());
		}
	}

	public ContactInfo loadContactInfo(Object userInfo) {
		return loadUserInfo(userInfo, (user) -> {
			try {
				return (ContactInfo) user.getClass().getMethod(GET_CONTACT_INFO).invoke(user);
			}
			catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
				throw new IllegalArgumentException(ex);
			}
		}, CONTACT);
	}

	public EmergencyContact loadEmergencyContact(Object userInfo) {
		return loadUserInfo(userInfo, (user) -> {
			try {
				return (EmergencyContact) user.getClass().getMethod(GET_CONTACT_INFO).invoke(user);
			}
			catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
				throw new IllegalArgumentException(ex);
			}
		}, EMERGENCY_CONTACT);
	}

	private <T> void getCommonResponse(T user, BasicUserResponse.BasicUserResponseBuilder response)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Gender gender = (Gender) user.getClass().getMethod("getGender").invoke(user);
		Title title = (Title) user.getClass().getMethod("getTitle").invoke(user);
		MaritalStatus maritalStatus = (MaritalStatus) user.getClass()
				.getMethod("getMaritalStatus").invoke(user);
		ContactInfo linkedContactInfo = (ContactInfo) user.getClass().getMethod(GET_CONTACT_INFO).invoke(user);
		response.secondaryEmail(linkedContactInfo.getSecondaryEmail());
		response.dateOfBirth((LocalDate) user.getClass().getMethod("getDateOfBirth").invoke(user));
		response.gender(String.valueOf(gender));
		response.maritalStatus(String.valueOf(maritalStatus));
		response.title(String.valueOf(title));
	}

	private <T> void getStudentResponse(T user, BasicUserResponse.BasicUserResponseBuilder response)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		getCommonResponse(user, response);
		response.studentId(Optional.ofNullable((Long) user.getClass().getMethod("getId").invoke(user)));
	}

	private <T> void getStaffResponse(T user, BasicUserResponse.BasicUserResponseBuilder response)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		getCommonResponse(user, response);
		response.staffId(Optional.ofNullable((Long) user.getClass().getMethod("getId").invoke(user)));
	}

	private <T> T loadUserInfo(Object userInfo, Function<Object, T> mapper, String exceptionMessage) {
		if (userInfo instanceof Optional<?> optional && optional.isPresent()) {
			Object user = optional.get();
			if (user instanceof Admin) {
				throw new EntityNotFoundException(String.format(NO_INFO_FOUND, exceptionMessage));
			}
			else if (user instanceof Staff || user instanceof Student) {
				return mapper.apply(user);
			}
		}
		throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
	}

}
