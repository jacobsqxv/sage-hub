package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.Application;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

	boolean existsByStudentUserId(Long userId);

	boolean existsByIdAndStudentUserId(Long id, Long userId);

	Optional<Application> findByStudentUserId(Long userId);
}
