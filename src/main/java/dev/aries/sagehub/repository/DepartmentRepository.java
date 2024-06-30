package dev.aries.sagehub.repository;

import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.specification.GenericSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
	boolean existsByCode(String code);

	boolean existsByName(String departmentName);

	default Page<Department> findAll(String name, String code, String status, Pageable page) {
		GenericSpecification<Department> spec = new GenericSpecification<>();
		return findAll(
				Specification
						.where(spec.hasName(name))
						.and(spec.hasCode(code))
						.and(spec.hasStatus(status)),
				page
		);
	}
}
