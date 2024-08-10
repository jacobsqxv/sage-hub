package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Long> {

	@Query("SELECT s.userProfile.contactInfo.secondaryEmail FROM Student s " +
			"WHERE s.user.id = :userId")
	Optional<String> findSecondaryEmailByUserId(Long userId);
}
