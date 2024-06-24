package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	boolean existsByUsername(String lowerCase);

}
