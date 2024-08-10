package dev.aries.sagehub.repository;

import java.util.List;
import java.util.Optional;

import dev.aries.sagehub.model.ExamResult;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantResultRepository extends JpaRepository<ExamResult, Long> {
	boolean existsByIndexNumber(String indexNumber);

	Optional<ExamResult> findByIdAndApplicantId(Long id, Long applicantId);

	List<ExamResult> findByApplicantId(Long applicantId);
}