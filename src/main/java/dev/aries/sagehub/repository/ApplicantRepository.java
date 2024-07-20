package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.Applicant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Applicant, Long> {
	Optional<Applicant> findByUserId(Long id);

}
