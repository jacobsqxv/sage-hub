package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Optional<Admin> findByUserId(Long id);
}
