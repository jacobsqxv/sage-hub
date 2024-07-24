package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	@Query("SELECT a.primaryEmail FROM Admin a WHERE a.user.id = :userId")
	Optional<String> findPrimaryEmailByUserId(Long userId);
}
