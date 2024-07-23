package dev.aries.sagehub.repository;

import java.util.Optional;
import java.util.UUID;

import dev.aries.sagehub.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByClientId(UUID clientId);
}
