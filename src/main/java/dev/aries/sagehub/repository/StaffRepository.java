package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.Staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StaffRepository extends JpaRepository<Staff, Long> {

	@Query("SELECT s.userProfile.contactInfo.secondaryEmail FROM Staff s " +
			"WHERE s.user.id = :userId")
	Optional<String> findSecondaryEmailByUserId(Long userId);
}
