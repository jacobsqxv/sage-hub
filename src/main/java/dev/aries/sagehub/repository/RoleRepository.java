package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleEnum name);

	boolean existsByName(RoleEnum name);
}
