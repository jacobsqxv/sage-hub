package dev.aries.sagehub.bootstrap;

import java.util.Arrays;
import java.util.Map;

import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.model.Admin;
import dev.aries.sagehub.model.Role;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.AdminRepository;
import dev.aries.sagehub.repository.RoleRepository;
import dev.aries.sagehub.repository.UserRepository;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.UserUtil;
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
	private final UserUtil userUtil;
	private final UserRepository userRepository;
	private final Generators generators;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.loadRoles();
		this.checkSuperAdmin();
	}

	private void loadRoles() {
		RoleEnum[] roles = RoleEnum.values();
		Map<RoleEnum, String> roleDescription = Map.of(
				RoleEnum.ADMIN, "Admin role", RoleEnum.STAFF, "Staff role",
				RoleEnum.STUDENT, "Student role", RoleEnum.SUPER_ADMIN, "Super admin role");

		Arrays.stream(roles).forEach((role) -> {
			if (!this.roleRepository.existsByName(role)) {
				Role roleToSave = Role.builder()
						.name(role)
						.description(roleDescription.get(role))
						.build();
				this.roleRepository.save(roleToSave);
				log.info("INFO - '{}' role added", role.name());
			}
		});
	}

	private void loadSuperAdmin() {
		String firstName = "Super";
		String lastName = "Admin";
		String username = this.generators.generateUsername(firstName, lastName);
		String password = this.generators.generatePassword();
		User user = this.userUtil.createNewUser(username, password, RoleEnum.SUPER_ADMIN);
		Admin superAdmin = Admin.builder()
				.firstName(firstName)
				.lastName(lastName)
				.primaryEmail("superadmin@email.com")
				.profilePictureUrl("https://www.gravatar.com/avatar.jpg")
				.user(user)
				.build();
		this.adminRepository.save(superAdmin);
		log.info("INFO - Super admin added with username: {} and password: {}", username, password);
	}

	private void checkSuperAdmin() {
		if (this.userRepository.findByUsername("sadmin").isPresent()) {
			log.info("INFO - Super admin already exists");
		}
		else {
			loadSuperAdmin();
		}
	}
}
