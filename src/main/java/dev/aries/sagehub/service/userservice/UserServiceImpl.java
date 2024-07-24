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
	private final ContactInfoInterface contactInfoInterface;
	private final EmergencyContactInterface emgContactInterface;
	private final BasicInfoInterface basicInfoInterface;
	private final UserFactory userFactory;
	private static final Integer STATUS_OK = HttpStatus.OK.value();
	private final UserMapper userMapper;

	@Override
	public GenericResponse changePassword(Long id, PasswordChangeRequest request) {
		User user = this.checks.currentlyLoggedInUser();
		validateLoggedInUser(user, id);
		if (!this.checks.isPasswordEqual(user, request.oldPassword())) {
			log.info("INFO - Current password is incorrect");
			throw new IllegalArgumentException(INVALID_CURRENT_PASSWORD);
		}
		user.setHashedPassword(this.passwordEncoder.encode(request.newPassword()));
		this.userRepository.save(user);
		return new GenericResponse(STATUS_OK, "Password changed successfully");
	}

	private void validateLoggedInUser(User user, Long id) {
		if (!Objects.equals(user.getId(), id)) {
			throw new UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}

	@Override
	@Transactional
	public UserResponse addFacultyMember(AddUserRequest request, String role) {
		switch (role) {
			case "STUDENT" -> {
				Student newStudent = (Student) buildInfo(request, role);
				log.info("INFO - Saving new student with ID: {}", newStudent.getId());
				this.checks.checkStudentExists(newStudent.getId());
				this.studentRepository.save(newStudent);
				return this.userMapper.toUserResponse(newStudent);
			}
			case "STAFF" -> {
				Staff newStaff = (Staff) buildInfo(request, role);
				log.info("INFO - Saving new staff with ID: {}", newStaff.getId());
				this.checks.checkStaffExists(newStaff.getId());
				this.staffRepository.save(newStaff);
				return this.userMapper.toUserResponse(newStaff);
			}
			default -> throw new IllegalArgumentException(String.format(NOT_FOUND, ROLE));
		}

	}
	/**
	 * This method builds a BaseUser object for a new user.
	 * It sets the basic info, contact info, emergency contact, id, and primary email.
	 * @param request the request containing the user information.
	 * @param role    the role of the new user (either "STUDENT" or "STAFF").
	 * @return a BaseUser object built with the provided role and request.
	 * @throws IllegalArgumentException if the role is not "STUDENT" or "STAFF".
	 */
	private BaseUser buildInfo(AddUserRequest request, String role) {
		Username username = this.generators.generateUsername(
				request.basicInfo().firstname(), request.basicInfo().lastname());
		Password password = this.generators.generatePassword(8);
		User user = this.userFactory.createNewUser(username, password, RoleEnum.valueOf(role));
		BasicInfo basicInfo = this.basicInfoInterface.addBasicInfo(request.basicInfo());
		ContactInfo contactInfo = this.contactInfoInterface.addContactInfo(request.contactInfo());
		EmergencyContact emergencyContact = this.emgContactInterface
				.addEmergencyContact(request.emergencyContact());
		Email primaryEmail = this.generators.generateUserEmail(user.getUsername(), role);
		Long id = this.generators.generateUniqueId(role.equals("STUDENT"));
		BaseUser.BaseUserBuilder<?, ?> builder = (role.equals("STUDENT") ? Student.builder() : Staff.builder())
				.id(id)
				.primaryEmail(primaryEmail.value())
				.basicInfo(basicInfo)
				.contactInfo(contactInfo)
				.emergencyContact(emergencyContact)
				.user(user);
		Email recipient = new Email(contactInfo.getSecondaryEmail());
		this.emailService.sendAccountCreatedEmail(username, password, recipient);
		log.info("INFO - New user added with username: {} and password: {}", username, password);
		return builder.build();
	}
}
