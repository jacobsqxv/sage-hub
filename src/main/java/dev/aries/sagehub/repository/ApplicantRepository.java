package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.Applicant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
	Optional<Applicant> findByUserId(Long id);

	boolean existsByIdAndUserId(Long applicantId, Long userId);

	boolean existsByUserId(Long userId);

	boolean existsByUserIdAndResultsId(Long userId, Long resultsId);
}
