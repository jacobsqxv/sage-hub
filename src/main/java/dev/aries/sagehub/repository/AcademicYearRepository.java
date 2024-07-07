package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.AcademicYear;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {
	Optional<AcademicYear> findByYear(Integer year);
}
