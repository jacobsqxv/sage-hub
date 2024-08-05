package dev.aries.sagehub.service.userservice;

import java.util.Objects;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.AddUserRequest;
import dev.aries.sagehub.dto.request.PasswordChangeRequest;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.dto.response.UserResponse;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.exception.UnauthorizedAccessException;
import dev.aries.sagehub.mapper.UserMapper;
import dev.aries.sagehub.model.BaseUser;
import dev.aries.sagehub.model.BasicInfo;
import dev.aries.sagehub.model.ContactInfo;
import dev.aries.sagehub.model.EmergencyContact;
import dev.aries.sagehub.model.Staff;
import dev.aries.sagehub.model.Student;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.repository.StaffRepository;
import dev.aries.sagehub.repository.StudentRepository;
import dev.aries.sagehub.repository.UserRepository;
import dev.aries.sagehub.service.basicinfoservice.BasicInfoInterface;
import dev.aries.sagehub.service.contactinfoservice.ContactInfoInterface;
import dev.aries.sagehub.service.emailservice.EmailService;
import dev.aries.sagehub.service.emgcontactservice.EmergencyContactInterface;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.EmailUtil;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.UserFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.aries.sagehub.constant.ExceptionConstants.INVALID_CURRENT_PASSWORD;
import static dev.aries.sagehub.constant.ExceptionConstants.NOT_FOUND;

/**
 * Implementation of the {@code UserService} interface.
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.userservice.UserService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private static final String ROLE = "Role";
	private final UserRepository userRepository;
	private final StudentRepository studentRepository;
	private final StaffRepository staffRepository;
	private final Checks checks;
	private final PasswordEncoder passwordEncoder;
	private final Generators generators;
	private final EmailService emailService;
	private final EmailUtil emailUtil;
	private final ContactInfoInterface contactInfoInterface;
	private final EmergencyContactInterface emgContactInterface;
	private final BasicInfoInterface basicInfoInterface;
	private final UserFactory userFactory;
	private static final Integer STATUS_OK = HttpStatus.OK.value();
	private final UserMapper userMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GenericResponse changePassword(Long id, PasswordChangeRequest request) {
		User user = checks.currentlyLoggedInUser();
		validateLoggedInUser(user, id);
		if (!checks.isPasswordEqual(user, request.oldPassword())) {
			log.info("Current password is incorrect");
			throw new IllegalArgumentException(INVALID_CURRENT_PASSWORD);
		}
		user.setHashedPassword(passwordEncoder.encode(request.newPassword()));
		userRepository.save(user);
		return new GenericResponse(STATUS_OK, "Password changed successfully");
	}

	private void validateLoggedInUser(User user, Long id) {
		if (!Objects.equals(user.getId(), id)) {
			throw new UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public UserResponse addFacultyMember(AddUserRequest request, String role) {
		Username username = generators.generateUsername(
				request.basicInfo().firstName(), request.basicInfo().lastName());
		Password password = generators.generatePassword(8);
		User user = userFactory.createNewUser(username, password, RoleEnum.valueOf(role));
		userRepository.save(user);
		switch (role) {
			case "STUDENT" -> {
				Student newStudent = (Student) buildInfo(user, request, role);
				log.info("Saving new student with ID: {}", newStudent.getId());
				checks.checkStudentExists(newStudent.getId());
				studentRepository.save(newStudent);
				sendEmail(user.getId(), username, password);
				return userMapper.toUserResponse(newStudent);
			}
			case "STAFF" -> {
				Staff newStaff = (Staff) buildInfo(user, request, role);
				log.info("Saving new staff with ID: {}", newStaff.getId());
				checks.checkStaffExists(newStaff.getId());
				staffRepository.save(newStaff);
				sendEmail(user.getId(), username, password);
				return userMapper.toUserResponse(newStaff);
			}
			default -> throw new IllegalArgumentException(String.format(NOT_FOUND, ROLE));
		}
	}
	/**
	 * This method builds a BaseUser object for a new user.
	 * It sets the basic info, contact info, emergency contact, id, and primary email.
	 * @param request the request containing the user information.
	 * @param role    the role of the new user (either "STUDENT" or "STAFF").
	 * @param user   the user object to be associated with the new user.
	 * @return a BaseUser object built with the provided role and request.
	 * @throws IllegalArgumentException if the role is not "STUDENT" or "STAFF".
	 */
	private BaseUser buildInfo(User user, AddUserRequest request, String role) {
		log.info("User ID: {}", user.getId());
		Long userId = user.getId();
		BasicInfo basicInfo = basicInfoInterface.addBasicInfo(request.basicInfo(), userId);
		ContactInfo contactInfo = contactInfoInterface.addContactInfo(request.contactInfo(), userId);
		EmergencyContact emergencyContact = emgContactInterface
				.addEmergencyContact(request.emergencyContact(), userId);
		Email primaryEmail = generators.generateUserEmail(user.getUsername(), role);
		Long id = generators.generateUniqueId(role.equals("STUDENT"));
		BaseUser.BaseUserBuilder<?, ?> builder = (role.equals("STUDENT") ? Student.builder() : Staff.builder())
				.id(id)
				.primaryEmail(primaryEmail.value())
				.basicInfo(basicInfo)
				.contactInfo(contactInfo)
				.emergencyContact(emergencyContact)
				.user(user);
		return builder.build();
	}

	private void sendEmail(Long userId, Username username, Password password) {
		Email recipient = emailUtil.getRecipient(userId);
		emailService.sendAccountCreatedEmail(username, password, recipient);
		log.info("User added:: username: {} | password: {}", username.value(), password.value());
	}
}
