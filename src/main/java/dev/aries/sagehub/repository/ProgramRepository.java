package dev.aries.sagehub.repository;

import dev.aries.sagehub.model.Program;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}
