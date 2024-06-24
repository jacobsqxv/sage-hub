package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.Staff;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {

	Optional<Staff> findByUserId(Long id);
}
