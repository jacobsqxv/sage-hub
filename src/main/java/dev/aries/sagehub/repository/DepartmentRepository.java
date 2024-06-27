package dev.aries.sagehub.repository;

import dev.aries.sagehub.model.Department;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	boolean existsByCode(String code);

	boolean existsByName(String departmentName);
}
