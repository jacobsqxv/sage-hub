package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

	Optional<Student> findByUserId(Long id);
}
