package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.EmergencyInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyContactRepository extends JpaRepository<EmergencyInfo, Long> {
	Optional<EmergencyInfo> findByUserId(Long userId);
}
