package dev.aries.sagehub.repository;

import dev.aries.sagehub.dto.search.GetProgramsPage;
import dev.aries.sagehub.enums.Degree;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.specification.GeneralSpecification;
import dev.aries.sagehub.specification.ProgramSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProgramRepository extends JpaRepository<Program, Long>, JpaSpecificationExecutor<Program> {
	@Modifying
	@Query("UPDATE Program p SET p.status = :status WHERE p.department.id = :departmentId")
	void updateStatusByDepartmentId(@Param("status") Status status, @Param("departmentId") Long departmentId);

	boolean existsByNameAndDegree(String name, Degree degree);

	default Page<Program> findAll(GetProgramsPage request, String status, Pageable page) {
		GeneralSpecification<Program> spec = new GeneralSpecification<>();
		return findAll(
				Specification
						.where(spec.hasName(request.name()))
						.and(ProgramSpecification.hasDepartment(request.department())
						.and(spec.hasStatus(status))),
				page
		);
	}
}
