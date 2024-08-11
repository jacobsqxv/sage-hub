package dev.aries.sagehub.repository;

import java.util.List;
import java.util.Optional;

import dev.aries.sagehub.model.ExamResult;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
	boolean existsByIndexNumber(String indexNumber);

	List<ExamResult> findAllByStudentId(Long id);

	Optional<ExamResult> findByIdAndStudentId(Long id, Long userId);
}
