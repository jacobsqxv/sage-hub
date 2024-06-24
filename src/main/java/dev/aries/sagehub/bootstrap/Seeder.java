package dev.aries.sagehub.bootstrap;

import java.util.Arrays;
import java.util.Map;

import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.model.Role;
import dev.aries.sagehub.repository.RoleRepository;
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

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.loadRoles();
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
				log.info("INFO - RoleSeeder: Role {} saved", role.name());
			}
		});
	}
}
