package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.Application;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	Optional<Application> findByUserId(Long id);

	Optional<Application> findByApplicantId(Long serialNumber);
}
