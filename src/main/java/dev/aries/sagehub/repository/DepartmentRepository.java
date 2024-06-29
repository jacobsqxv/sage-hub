package dev.aries.sagehub.repository;

import dev.aries.sagehub.model.Department;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import static dev.aries.sagehub.specification.DepartmentSpecification.hasCode;
import static dev.aries.sagehub.specification.DepartmentSpecification.hasName;
import static dev.aries.sagehub.specification.DepartmentSpecification.hasStatus;

public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
	boolean existsByCode(String code);

	boolean existsByName(String departmentName);

	default Page<Department> findAll(String name, String code, String status, Pageable page) {
		return findAll(
				Specification.where(hasName(name))
						.and(hasCode(code))
						.and(hasStatus(status)),
				page
		);
	}
}
