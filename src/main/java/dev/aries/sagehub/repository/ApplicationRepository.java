package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.Application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicantRepository extends JpaRepository<Application, Long> {

	boolean existsByUserId(Long userId);

	boolean existsByUserIdAndResultsId(Long userId, Long resultsId);

	@Query("SELECT a.contactInfo.secondaryEmail FROM Application a " +
			"WHERE a.user.id = :userId")
	Optional<String> findSecondaryEmailByUserId(Long userId);
}
