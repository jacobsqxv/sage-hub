package dev.aries.sagehub.factory;

import java.util.ArrayList;
import java.util.UUID;

import dev.aries.sagehub.dto.request.ApplicantInfoRequest;
import dev.aries.sagehub.dto.request.DepartmentRequest;
import dev.aries.sagehub.dto.request.FacultyMemberRequest;
import dev.aries.sagehub.enums.AccountStatus;
import dev.aries.sagehub.enums.ApplicationStatus;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.mapper.UserProfileMapper;
import dev.aries.sagehub.model.Application;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.model.Role;
import dev.aries.sagehub.model.Student;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.Voucher;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.repository.RoleRepository;
import dev.aries.sagehub.repository.UserRepository;
import dev.aries.sagehub.service.userservice.UserService;
import dev.aries.sagehub.util.Generators;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static dev.aries.sagehub.constant.ExceptionConstants.NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class ModelFactory {
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final Generators generators;

	public Department createNewDept(DepartmentRequest request) {
		Department department = Department.builder()
				.name(request.name())
				.code(generators.generateDeptCode())
				.programs(new ArrayList<>())
				.status(Status.PENDING)
				.build();
		log.info("Creating new department...");
		return department;
	}

	public Application createNewApplication(ApplicantInfoRequest request, User user, Voucher voucher) {
		FacultyMemberRequest facultyMember = new FacultyMemberRequest(
				request.applicantInfo(),
				request.guardianInfo(),
				user
		);
		Student newApplicant = (Student) userService.addFacultyMember(
				facultyMember, voucher.getSerialNumber());
		Application application = Application.builder()
				.yearOfApplication(voucher.getAcademicYear())
				.applicant(newApplicant)
				.education(UserProfileMapper.toEducation(request.educationBackground()))
				.examResults(new ArrayList<>())
				.programChoices(new ArrayList<>())
				.isSubmitted(false)
				.status(ApplicationStatus.PENDING)
				.build();
		log.info("Creating new application...");
		return application;
	}

	public User createNewUser(Username username, Password password, RoleEnum roleEnum) {
		User user = User.builder()
				.username(username.value())
				.clientId(generateClientId())
				.hashedPassword(passwordEncoder.encode(password.value()))
				.accountEnabled(true)
				.role(getRole(roleEnum))
				.failedLoginAttempts(0)
				.status(AccountStatus.ACTIVE)
				.build();
		log.info("Creating new user: {}...", user.getUsername());
		return user;
	}

	private UUID generateClientId() {
		UUID clientId = UUID.randomUUID();
		while (userRepository.existsByClientId(clientId)) {
			clientId = UUID.randomUUID();
		}
		return clientId;
	}

	private Role getRole(RoleEnum name) {
		return roleRepository.findByName(name)
				.orElseThrow(() -> new EntityNotFoundException(String.format(NOT_FOUND, "Role")));
	}

}
