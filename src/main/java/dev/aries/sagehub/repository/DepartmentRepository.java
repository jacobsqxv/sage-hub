package dev.aries.sagehub.repository;

import dev.aries.sagehub.dto.search.GetDepartmentsPage;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.specification.GeneralSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
	boolean existsByCode(String code);

	boolean existsByName(String departmentName);

	default Page<Department> findAll(GetDepartmentsPage request, String status, Pageable page) {
		GeneralSpecification<Department> spec = new GeneralSpecification<>();
		return findAll(
				Specification
						.where(spec.hasName(request.name()))
						.and(spec.hasCode(request.code()))
						.and(spec.hasStatus(status)),
				page
		);
	}
}
