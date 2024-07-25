package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.Applicant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

	boolean existsByIdAndUserId(Long applicantId, Long userId);

	boolean existsByUserId(Long userId);

	boolean existsByUserIdAndResultsId(Long userId, Long resultsId);

	@Query("SELECT a.contactInfo.secondaryEmail FROM Applicant a " +
			"WHERE a.user.id = :userId")
	Optional<String> findSecondaryEmailByUserId(Long userId);
}
