package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.EmergencyContact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {
	Optional<EmergencyContact> findByUserId(Long userId);
}
