package dev.aries.sagehub.util;

import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.model.Role;
import dev.aries.sagehub.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import static dev.aries.sagehub.constant.ExceptionConstants.NOT_FOUND;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleUtil {

	private final RoleRepository roleRepository;

	public Role getRole(RoleEnum name) {
		return roleRepository.findByName(name)
				.orElseThrow(() -> new EntityNotFoundException(String.format(NOT_FOUND, "Role")));
	}

}
