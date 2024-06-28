package dev.aries.sagehub.util;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Optional;

import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.enums.Status;
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

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleUtil roleUtil;
	private final AdminRepository adminRepository;
	private final StaffRepository staffRepository;
	private final StudentRepository studentRepository;
	private static final String USER = "User";
	public static final String CONTACT = "contact";
	public static final String EMERGENCY_CONTACT = "emergency contact";
	private final Generators generators;

	public User getUser(String username) {
		return this.userRepository.findByUsername(username)
			.orElseThrow(
					() -> new EntityNotFoundException(String.format(NOT_FOUND, USER)));
	}

	public User getUser(Long id) {
		return this.userRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format(NOT_FOUND, USER)));
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
			if (user instanceof Admin admin) {
				return getUserResponse(admin);
			}
			else if (user instanceof Staff staff) {
				return getUserResponse(staff);
			}
			else if (user instanceof Student student) {
				return getUserResponse(student);
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
					.profilePicture((String) user.getClass().getMethod("getProfilePictureUrl").invoke(user))
					.fullname((String) user.getClass().getMethod("fullName").invoke(user))
					.username(linkedUser.getUsername())
					.primaryEmail((String) user.getClass().getMethod("getPrimaryEmail").invoke(user))
					.secondaryEmail(null)
					.status(linkedUser.getStatus().getValue())
					.role(linkedUser.getRole().getName().name());
			if (user instanceof Student || user instanceof Staff) {
				Enum<Gender> gender;
				gender = (Enum<Gender>) user.getClass().getMethod("getGender").invoke(user);
				ContactInfo linkedContactInfo = (ContactInfo) user.getClass()
						.getMethod("getContactInfo").invoke(user);
				response.secondaryEmail(linkedContactInfo.getSecondaryEmail());
				response.dateOfBirth((LocalDate) user.getClass()
						.getMethod("getDateOfBirth").invoke(user));
				response.gender(String.valueOf(gender));
			}
			if (user instanceof Student) {
				response.studentId(Optional.ofNullable((Long) user.getClass()
						.getMethod("getId").invoke(user)));
			}
			if (user instanceof Staff) {
				response.staffId(Optional.ofNullable((Long) user.getClass()
						.getMethod("getId").invoke(user)));
			}
			return response.build();
		}
		catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
			throw new IllegalStateException(ex.getMessage());
		}
	}

	public ContactInfo loadContactInfo(Object userInfo) {
		if (userInfo instanceof Optional<?> optional && optional.isPresent()) {
			Object user = optional.get();
			if (user instanceof Admin) {
				throw new EntityNotFoundException(String.format(NO_INFO_FOUND, CONTACT));
			}
			else if (user instanceof Staff || user instanceof Student) {
				try {
					return (ContactInfo) user.getClass().getMethod("getContactInfo").invoke(user);
				}
				catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
					throw new IllegalStateException(ex.getMessage());
				}
			}
		}
		throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
	}

	public EmergencyContact loadEmergencyContact(Object userInfo) {
		if (userInfo instanceof Optional<?> optional && optional.isPresent()) {
			Object user = optional.get();
			if (user instanceof Admin) {
				throw new EntityNotFoundException(String.format(NO_INFO_FOUND, EMERGENCY_CONTACT));
			}
			else if (user instanceof Staff || user instanceof Student) {
				try {
					return (EmergencyContact) user.getClass().getMethod("getContactInfo").invoke(user);
				}
				catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
					throw new IllegalStateException(ex.getMessage());
				}
			}
		}
		throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
	}

}
