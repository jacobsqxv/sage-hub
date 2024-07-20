package dev.aries.sagehub.util;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

import dev.aries.sagehub.dto.response.BasicInfoResponse;
import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.enums.AccountStatus;
import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.MaritalStatus;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.enums.Title;
import dev.aries.sagehub.model.Admin;
import dev.aries.sagehub.model.Applicant;
import dev.aries.sagehub.model.ContactInfo;
import dev.aries.sagehub.model.EmergencyContact;
import dev.aries.sagehub.model.Role;
import dev.aries.sagehub.model.Staff;
import dev.aries.sagehub.model.Student;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.AdminRepository;
import dev.aries.sagehub.repository.ApplicantRepository;
import dev.aries.sagehub.repository.StaffRepository;
import dev.aries.sagehub.repository.StudentRepository;
import dev.aries.sagehub.repository.UserRepository;
import dev.aries.sagehub.strategy.response.UserResponseStrategy;
import dev.aries.sagehub.strategy.response.UserResponseStrategyConfig;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	private final UserResponseStrategyConfig userResponseStratConfig;
	private final AdminRepository adminRepository;
	private final StaffRepository staffRepository;
	private final StudentRepository studentRepository;
	private final ApplicantRepository applicationRepository;

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
			case APPLICANT -> {
				return this.applicationRepository.findByUserId(id);
			}
			default -> throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
		}
	}

	public BasicUserResponse getBasicInfo(Object userInfo) {
		return extractUserFromOptional(userInfo)
				.map(this::getUserResponseFromUserObject)
				.orElseThrow(() -> new IllegalArgumentException(String.format(NOT_FOUND, USER)));
	}

	private Optional<Object> extractUserFromOptional(Object userInfo) {
		if (userInfo instanceof Optional<?> optional) {
			return (Optional<Object>) optional;
		}
		return Optional.empty();
	}

	private BasicUserResponse getUserResponseFromUserObject(Object user) {
		UserResponseStrategy strategy = this.userResponseStratConfig.responseStrategies().get(user.getClass());
		if (strategy != null) {
			return strategy.getUserResponse(user);
		}
		else {
			throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
		}
	}

	public <T> BasicUserResponse getUserResponse(T user) {
		if (user == null) {
			throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
		}
		try {
			User linkedUser = (User) user.getClass().getMethod("getUser").invoke(user);
			BasicUserResponse.BasicUserResponseBuilder response = BasicUserResponse.builder()
					.userId(linkedUser.getId())
					.username(linkedUser.getUsername())
					.primaryEmail((String) user.getClass()
							.getMethod("getPrimaryEmail").invoke(user))
					.status(linkedUser.getStatus().toString())
					.role(linkedUser.getRole().getName().name());
			if (user instanceof Admin) {
				String fullName = (String) user.getClass().getMethod("fullName").invoke(user);
				response.basicInfo(BasicInfoResponse.builder()
						.fullName(fullName)
						.build());
			}
			else {
				getCommonResponse(user, response);
			}
			return response.build();
		}
		catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
			throw new IllegalStateException(ex.getMessage());
		}
	}

	public <T> T loadUserInfoGeneric(Object userInfo, Class<T> infoClass) {
		return loadUserInfo(userInfo, (user) -> {
			try {
				return infoClass.cast(user.getClass().getMethod(GET_CONTACT_INFO).invoke(user));
			}
			catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
				throw new IllegalArgumentException(ex);
			}
		}, (infoClass == ContactInfo.class) ? CONTACT : EMERGENCY_CONTACT);
	}

	public ContactInfo loadContactInfo(Object userInfo) {
		return loadUserInfoGeneric(userInfo, ContactInfo.class);
	}

	public EmergencyContact loadEmergencyContact(Object userInfo) {
		return loadUserInfoGeneric(userInfo, EmergencyContact.class);
	}

	private <T> void getCommonResponse(T user, BasicUserResponse.BasicUserResponseBuilder response)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Gender gender = (Gender) user.getClass().getMethod("getGender").invoke(user);
		Title title = (Title) user.getClass().getMethod("getTitle").invoke(user);
		MaritalStatus maritalStatus = (MaritalStatus) user.getClass()
				.getMethod("getMaritalStatus").invoke(user);
		ContactInfo linkedContactInfo = (ContactInfo) user.getClass().getMethod(GET_CONTACT_INFO).invoke(user);
		response.secondaryEmail(linkedContactInfo.getSecondaryEmail());
		BasicInfoResponse basicInfoResponse = BasicInfoResponse.builder()
				.profilePictureUrl((String) user.getClass()
						.getMethod("getProfilePictureUrl").invoke(user))
				.title(String.valueOf(title))
				.fullName((String) user.getClass().getMethod("fullName").invoke(user))
				.gender(String.valueOf(gender))
				.maritalStatus(String.valueOf(maritalStatus))
				.dateOfBirth((LocalDate) user.getClass().getMethod("getDateOfBirth").invoke(user))
				.build();
		response.basicInfo(basicInfoResponse);
		response.memberId((Long) user.getClass().getMethod("getId").invoke(user));
	}

	private <T> T loadUserInfo(Object userInfo, Function<Object, T> mapper, String exceptionMessage) {
		if (!(userInfo instanceof Optional<?> optional)) {
			throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
		}
		if (optional.isEmpty()) {
			throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
		}
		Object user = optional.get();
		if (user instanceof Admin) {
			throw new EntityNotFoundException(String.format(NO_INFO_FOUND, exceptionMessage));
		}
		return mapper.apply(user);
	}

}
