package dev.aries.sagehub.repository;

import dev.aries.sagehub.dto.search.GetCrseOffrgPage;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.model.CourseOffering;
import dev.aries.sagehub.model.attribute.AcademicPeriod;
import dev.aries.sagehub.specification.GeneralSpecification;
import dev.aries.sagehub.specification.ProgramCourseSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProgramCourseRepository extends JpaRepository<CourseOffering, Long>,
		JpaSpecificationExecutor<CourseOffering> {

	@Modifying
	@Query("UPDATE CourseOffering p SET p.status = :status WHERE p.program.id = :programId")
	void updateStatusByProgramId(@Param("status") Status status, @Param("programId") Long programId);

	boolean existsByProgramIdAndCourseIdAndAcademicPeriod(Long programId, Long courseId, AcademicPeriod period);

	default Page<CourseOffering> findAll(Program program, GetCrseOffrgPage request, Pageable pageable) {
		GeneralSpecification<CourseOffering> spec = new GeneralSpecification<>();
		return findAll(
				Specification
						.where(ProgramCourseSpecification.hasProgram(program))
						.and(ProgramCourseSpecification.hasCourse(request.course()))
						.and(ProgramCourseSpecification.hasYear(request.year()))
						.and(ProgramCourseSpecification.hasSemester(request.semester()))
						.and(spec.hasStatus(request.status())),
				pageable
		);
	}

	boolean existsByProgramIdAndCourseId(Long programId, Long courseId);

	int deleteByIdAndProgramId(Long id, Long programId);
}
