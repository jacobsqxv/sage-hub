package dev.aries.sagehub.service.userservice;

import dev.aries.sagehub.dto.request.FacultyMemberRequest;
import dev.aries.sagehub.dto.request.PasswordChangeRequest;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.model.BaseUser;
import dev.aries.sagehub.model.EmergencyInfo;
import dev.aries.sagehub.model.Staff;
import dev.aries.sagehub.model.Student;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.UserProfile;
import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.repository.StaffRepository;
import dev.aries.sagehub.repository.StudentRepository;
import dev.aries.sagehub.repository.UserRepository;
import dev.aries.sagehub.service.emailservice.EmailService;
import dev.aries.sagehub.service.emergencyinfoservice.EmergencyInfoService;
import dev.aries.sagehub.service.userprofileservice.UserProfileService;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.EmailUtil;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.UserUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.aries.sagehub.constant.ExceptionConstants.ALREADY_EXISTS;
import static dev.aries.sagehub.constant.ExceptionConstants.INVALID_CURRENT_PASSWORD;
import static dev.aries.sagehub.constant.ExceptionConstants.UNEXPECTED_VALUE;

/**
 * Implementation of the {@code UserService} interface.
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.userservice.UserService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final EmergencyInfoService emergencyInfoService;
	private final StudentRepository studentRepository;
	private final StaffRepository staffRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserProfileService userProfileService;
	private final UserRepository userRepository;
	private final EmailService emailService;
	private final EmailUtil emailUtil;
	private final UserUtil userUtil;
	private final Checks checks;
	private static final Integer STATUS_OK = HttpStatus.OK.value();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GenericResponse changePassword(Long id, PasswordChangeRequest request) {
		User user = userUtil.currentlyLoggedInUser();
		Checks.validateLoggedInUserId(user, id);
		if (!checks.isPasswordEqual(user, request.oldPassword())) {
			log.info("Current password is incorrect");
			throw new IllegalArgumentException(INVALID_CURRENT_PASSWORD);
		}
		user.setHashedPassword(passwordEncoder.encode(request.newPassword()));
		userRepository.save(user);
		return new GenericResponse(STATUS_OK, "Password changed successfully");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public BaseUser addFacultyMember(FacultyMemberRequest request, Long memberId) {
		switch (request.user().getRole().getName()) {
			case RoleEnum.APPLICANT -> {
				Student newStudent = (Student) buildInfo(request, memberId);
				log.info("Saving new student with ID: {}", newStudent.getId());
				checkStudentExists(newStudent.getId());
//				studentRepository.save(newStudent);
//				sendEmail(user.getId(), username, password);
				return newStudent;
			}
			case RoleEnum.STAFF -> {
				Staff newStaff = (Staff) buildInfo(request, memberId);
				log.info("Saving new staff with ID: {}", newStaff.getId());
				checkStaffExists(newStaff.getId());
//				staffRepository.save(newStaff);
//				sendEmail(user.getId(), username, password);
				return newStaff;
			}
			default -> throw new IllegalArgumentException(UNEXPECTED_VALUE);
		}
	}
	/**
	 * This method builds a BaseUser object for a new user.
	 * It sets the basic info, contact info, emergency contact, id, and primary email.
	 * @param request the request containing the user information.
	 * @param memberId the ID of the user.
	 * @return a BaseUser object built with the provided role and request.
	 * @throws IllegalArgumentException if the role is not "STUDENT" or "STAFF".
	 */
	private BaseUser buildInfo(FacultyMemberRequest request, Long memberId) {
		log.info("User ID: {}", request.user().getId());
		User user = request.user();
		RoleEnum role = user.getRole().getName();
		Long userId = request.user().getId();
		UserProfile userProfile = userProfileService.addUserProfile(request.userProfile(), userId);
		EmergencyInfo emergencyInfo = emergencyInfoService
				.addEmergencyInfo(request.emergencyInfo(), userId);
		Email primaryEmail = Generators.generateUserEmail(request.user().getUsername(),
				role);
		boolean isStudent = role.equals(RoleEnum.APPLICANT);
		BaseUser.BaseUserBuilder<?, ?> builder = (isStudent ? Student.builder() : Staff.builder())
				.id(memberId)
				.primaryEmail(primaryEmail.value())
				.userProfile(userProfile)
				.emergencyInfo(emergencyInfo)
				.user(user);
		return builder.build();
	}

//	private void sendEmail(Long userId, Username username, Password password) {
//		Email recipient = emailUtil.getRecipient(userId);
//		emailService.sendAccountCreatedEmail(username, password, recipient);
//		log.info("User added:: username: {} | password: {}", username.value(), password.value());
//	}

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

}
