package dev.aries.sagehub.service.userservice;

import dev.aries.sagehub.dto.request.AddUserRequest;
import dev.aries.sagehub.dto.request.ContactInfoRequest;
import dev.aries.sagehub.dto.request.EmergencyContactRequest;
import dev.aries.sagehub.dto.request.PasswordChangeRequest;
import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.dto.response.ContactInfoResponse;
import dev.aries.sagehub.dto.response.EmergencyContactResponse;
import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.mapper.ContactInfoMapper;
import dev.aries.sagehub.mapper.EmergencyContactMapper;
import dev.aries.sagehub.model.BasicInfo;
import dev.aries.sagehub.model.ContactInfo;
import dev.aries.sagehub.model.EmergencyContact;
import dev.aries.sagehub.model.Staff;
import dev.aries.sagehub.model.Student;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.ContactInfoRepository;
import dev.aries.sagehub.repository.EmergencyContactRepository;
import dev.aries.sagehub.repository.StaffRepository;
import dev.aries.sagehub.repository.StudentRepository;
import dev.aries.sagehub.repository.UserRepository;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.GlobalUtil;
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static dev.aries.sagehub.constant.ExceptionConstants.INVALID_CURRENT_PASSWORD;
import static dev.aries.sagehub.constant.ExceptionConstants.INVALID_ROLE;

/**
 * UserServiceImpl is a service class that implements the UserService interface.
 * It provides methods for managing users, including changing passwords,
 * adding faculty members, and managing contact information.
 * @author Jacobs A
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final StudentRepository studentRepository;
	private final StaffRepository staffRepository;
	private final ContactInfoRepository contactInfoRepository;
	private final UserUtil userUtil;
	private final GlobalUtil globalUtil;
	private final PasswordEncoder passwordEncoder;
	private final Generators generators;
	private final ContactInfoMapper contactInfoMapper;
	private final EmergencyContactMapper emergencyContactMapper;
	private final EmergencyContactRepository emergencyContactRepository;

	/**
	 * Changes the password of a user.
	 * @param id the ID of the user.
	 * @param request the request containing the old and new passwords.
	 * @return a BasicUserResponse containing the updated user information.
	 */
	@Override
	public BasicUserResponse changePassword(Long id, PasswordChangeRequest request) {
		this.userUtil.isCurrentlyLoggedInUser(id);
		if (!this.userUtil.isPasswordValid(request.oldPassword())) {
			log.info("INFO - Current password is incorrect");
			throw new IllegalArgumentException(INVALID_CURRENT_PASSWORD);
		}
		User user = this.userUtil.getUser(id);
		user.setHashedPassword(this.passwordEncoder.encode(request.newPassword()));
		this.userRepository.save(user);
		Object userInfo = this.globalUtil.getUserInfo(id);
		return this.globalUtil.getBasicInfo(userInfo);
	}

	/**
	 * Adds a new faculty member.
	 * @param request the request containing the user information.
	 * @param role the role of the new faculty member (either "STUDENT" or "STAFF").
	 * @return a BasicUserResponse containing the new faculty member's information.
	 */
	@Override
	public BasicUserResponse addFacultyMember(AddUserRequest request, String role) {
		switch (role) {
			case "STUDENT" -> {
				Student newStudent = (Student) buildInfo(request, role);
				this.studentRepository.save(newStudent);
				return this.globalUtil.getUserResponse(newStudent);
			}
			case "STAFF" -> {
				Staff newStaff = (Staff) buildInfo(request, role);
				this.staffRepository.save(newStaff);
				return this.globalUtil.getUserResponse(newStaff);
			}
			default -> throw new IllegalArgumentException(INVALID_ROLE);
		}
	}

	/**
	 * Retrieves the contact information of a user.
	 * @param id the ID of the user.
	 * @return a ContactInfoResponse containing the user's contact information.
	 */
	@Override
	public ContactInfoResponse getContactInfo(Long id) {
		this.userUtil.isAdminOrLoggedIn(id);
		ContactInfo contactInfo = this.globalUtil.loadContactInfo(this.globalUtil.getUserInfo(id));

		return this.contactInfoMapper.toContactInfoResponse(contactInfo);
	}

	/**
	 * Updates the contact information of a user.
	 * @param id the ID of the user.
	 * @param request the request containing the new contact information.
	 * @return a ContactInfoResponse containing the updated contact information.
	 */
	@Override
	public ContactInfoResponse updateContactInfo(Long id, ContactInfoRequest request) {

		this.userUtil.isAdminOrLoggedIn(id);
		ContactInfo contactInfo = this.globalUtil.loadContactInfo(this.globalUtil.getUserInfo(id));
		UpdateStrategy strategy = globalUtil.checkStrategy("updateContactInfo");
		contactInfo = (ContactInfo) strategy.update(contactInfo, request);
		this.contactInfoRepository.save(contactInfo);
		log.info("INFO - Contact info with ID:{} updated", contactInfo.getId());
		return this.contactInfoMapper.toContactInfoResponse(contactInfo);
	}

	/**
	 * Retrieves the emergency contact information of a user.
	 * @param id the ID of the user.
	 * @return an EmergencyContactResponse containing the user's emergency contact information.
	 */
	@Override
	public EmergencyContactResponse getEmergencyContact(Long id) {
		this.userUtil.isAdminOrLoggedIn(id);
		EmergencyContact emergencyContact = this.globalUtil.loadEmergencyContact(
				this.globalUtil.getUserInfo(id));
		return this.emergencyContactMapper.toEmergencyContactResponse(emergencyContact);
	}
	/**
	 * Updates the emergency contact information of a user.
	 * @param id the ID of the user.
	 * @param request the request containing the new emergency contact information.
	 * @return a EmergencyContactResponse containing the updated emergency contact information.
	 */
	@Override
	public EmergencyContactResponse updateEmergencyContact(Long id, EmergencyContactRequest request) {
		this.userUtil.isAdminOrLoggedIn(id);
		EmergencyContact emergencyContact = this.globalUtil.loadEmergencyContact(
				this.globalUtil.getUserInfo(id));
		UpdateStrategy strategy = globalUtil.checkStrategy("updateEmergencyContact");
		emergencyContact = (EmergencyContact) strategy.update(emergencyContact, request);
		this.emergencyContactRepository.save(emergencyContact);
		log.info("INFO - Emergency contact with ID{} updated", emergencyContact.getId());
		return this.emergencyContactMapper.toEmergencyContactResponse(emergencyContact);
	}

	/**
	 * This method builds a BasicInfo object using the provided builder and request.
	 * It sets the first name, middle name, last name, gender, and date of birth from the request.
	 *
	 * @param <T> a type that extends BasicInfo.BasicInfoBuilder.
	 * @param builder the builder used to construct the BasicInfo object.
	 * @param request the request containing the user information.
	 * @return a BasicInfo object built with the provided builder and request.
	 */
	private <T extends BasicInfo.BasicInfoBuilder<?, ?>> BasicInfo buildBasicInfo(
			T builder, AddUserRequest request) {
		String firstname = request.firstname();
		String lastname = request.lastname();
		return builder.firstName(firstname)
			.middleName(request.middleName())
			.lastName(lastname)
			.gender(Gender.valueOf(request.gender()))
			.dateOfBirth(request.dateOfBirth())
			.profilePictureUrl(request.profilePicture())
			.build();
	}

	/**
	 * This method builds a BasicInfo object for a new user. It first creates a new User
	 * object with the provided first name, last name, and role. Then, it adds the
	 * secondary email to the contact information. It generates a primary email for the
	 * user based on their username and role. Depending on the role, it builds a Student
	 * or Staff object with the generated ID, primary email, contact information, and
	 * user. Finally, it uses the buildBasicInfo method to set the first name, middle
	 * name, last name, gender, and date of birth from the request.
	 * @param request the request containing the user information.
	 * @param role the role of the new user (either "STUDENT" or "STAFF").
	 * @return a BasicInfo object built with the provided builder and request.
	 * @throws IllegalArgumentException if the role is not "STUDENT" or "STAFF".
	 */
	private BasicInfo buildInfo(AddUserRequest request, String role) {
		User user = this.userUtil.createNewUser(
				request.firstname(), request.lastname(), RoleEnum.valueOf(role));
		Long id = addContactInfo(request.contactInfo());
		ContactInfo contactInfo = this.contactInfoRepository.findById(id)
				.orElseThrow();
		String primaryEmail = this.generators.generateUserEmail(user.getUsername(), role);
		BasicInfo.BasicInfoBuilder<?, ?> builder = switch (role) {
			case "STUDENT" -> Student.builder()
					.id(this.generators.generateUniqueId(true))
					.primaryEmail(primaryEmail)
					.contactInfo(contactInfo)
					.user(user);
			case "STAFF" -> Staff.builder()
					.id(this.generators.generateUniqueId(false))
					.primaryEmail(primaryEmail)
					.contactInfo(contactInfo)
					.user(user);
			default -> throw new IllegalArgumentException(INVALID_ROLE);
		};
		return buildBasicInfo(builder, request);
	}

	private Long addContactInfo(ContactInfoRequest request) {
		ContactInfo contactInfo = ContactInfo.builder()
				.secondaryEmail(request.secondaryEmail())
				.phoneNumber(request.phoneNumber())
				.address(request.address())
				.city(request.city())
				.region(request.region())
				.build();
		return this.contactInfoRepository.save(contactInfo).getId();
	}
}
