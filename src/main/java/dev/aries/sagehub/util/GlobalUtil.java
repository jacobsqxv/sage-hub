package dev.aries.sagehub.util;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Optional;

import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.enums.Gender;
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
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import static dev.aries.sagehub.constant.ExceptionConstants.NO_CONTACT_INFO;
import static dev.aries.sagehub.constant.ExceptionConstants.NO_EMERGENCY_CONTACT;
import static dev.aries.sagehub.constant.ExceptionConstants.NO_USER_FOUND;

@Component
@RequiredArgsConstructor
@Slf4j
public class GlobalUtil {

	private final UserUtil userUtil;

	private final AdminRepository adminRepository;

	private final StaffRepository staffRepository;

	private final StudentRepository studentRepository;

	public Object getUserInfo(Long id) {
		Role userRole = this.userUtil.getUser(id).getRole();
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
			default -> throw new IllegalArgumentException(NO_USER_FOUND);
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
		throw new IllegalArgumentException(NO_USER_FOUND);
	}

	public <T> BasicUserResponse getUserResponse(T user) {
		if (user == null) {
			throw new IllegalArgumentException(NO_USER_FOUND);
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
				throw new EntityNotFoundException(NO_CONTACT_INFO);
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
		throw new IllegalArgumentException(NO_USER_FOUND);
	}

	public EmergencyContact loadEmergencyContact(Object userInfo) {
		if (userInfo instanceof Optional<?> optional && optional.isPresent()) {
			Object user = optional.get();
			if (user instanceof Admin) {
				throw new EntityNotFoundException(NO_EMERGENCY_CONTACT);
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
		throw new IllegalArgumentException(NO_USER_FOUND);
	}
}
