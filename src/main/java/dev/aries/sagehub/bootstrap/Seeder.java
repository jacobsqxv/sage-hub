package dev.aries.sagehub.bootstrap;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.model.AcademicYear;
import dev.aries.sagehub.model.Admin;
import dev.aries.sagehub.model.Role;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.repository.AcademicYearRepository;
import dev.aries.sagehub.repository.AdminRepository;
import dev.aries.sagehub.repository.RoleRepository;
import dev.aries.sagehub.repository.UserRepository;
import dev.aries.sagehub.service.emailservice.EmailService;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.UserFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Seeder implements ApplicationListener<ContextRefreshedEvent> {

	private final RoleRepository roleRepository;
	private final AdminRepository adminRepository;
	private final UserFactory userFactory;
	private final UserRepository userRepository;
	private final Generators generators;
	private final EmailService emailService;
	private final AcademicYearRepository academicYearRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		loadRoles();
		checkAcademicYear();
		checkSuperAdmin();
	}

	private void loadAcademicYear(Integer year) {
		LocalDate startDate = LocalDate.of(year, 9, 1);
		LocalDate endDate = startDate.plusYears(1).minusDays(1);
		academicYearRepository.save(AcademicYear.builder()
				.year(year)
				.startDate(startDate)
				.endDate(endDate)
				.build());
		log.info("Academic year added:: {}", year);
	}

	private void loadRoles() {
		RoleEnum[] roles = RoleEnum.values();
		Map<RoleEnum, String> roleDescription = Map.of(
				RoleEnum.SUPER_ADMIN, "Super admin role",
				RoleEnum.ADMIN, "Admin role",
				RoleEnum.STAFF, "Staff role",
				RoleEnum.STUDENT, "Student role",
				RoleEnum.APPLICANT, "Prospective Student role");

		Arrays.stream(roles).forEach((role) -> {
			if (!roleRepository.existsByName(role)) {
				Role roleToSave = Role.builder()
						.name(role)
						.description(roleDescription.get(role))
						.build();
				roleRepository.save(roleToSave);
				log.info("Role added:: '{}'", role.name());
			}
		});
	}

	private void loadSuperAdmin() {
		String firstName = "Super";
		String lastName = "Admin";
		Username username = generators.generateUsername(firstName, lastName);
		Password password = generators.generatePassword(8);
		Email email = new Email("sagehub.superadmin@mockinbox.com");
		User user = userFactory.createNewUser(username, password, RoleEnum.SUPER_ADMIN);
		Admin superAdmin = Admin.builder()
				.firstName(firstName)
				.lastName(lastName)
				.primaryEmail(email.value())
				.profilePictureUrl("https://www.gravatar.com/avatar.jpg")
				.user(user)
				.build();
		adminRepository.save(superAdmin);
		emailService.sendAccountCreatedEmail(username, password, email);
		log.info("Super admin added:: username: {} | password: {}", username.value(), password.value());
	}

	private void checkSuperAdmin() {
		if (userRepository.findByUsername("sadmin").isPresent()) {
			log.info("Super admin already exists");
		}
		else {
			loadSuperAdmin();
		}
	}

	private void checkAcademicYear() {
		Integer currentYear = LocalDate.now().getYear();
		if (academicYearRepository.findByYear(currentYear).isPresent()) {
			log.info("Academic year {} already exists", currentYear);
		}
		else {
			loadAcademicYear(currentYear);
		}
	}
}
