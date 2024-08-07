package dev.aries.sagehub.repository;

import dev.aries.sagehub.dto.search.GetPrgCoursesPage;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.model.AcademicPeriod;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.model.ProgramCourse;
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


public interface ProgramCourseRepository extends JpaRepository<ProgramCourse, Long>,
		JpaSpecificationExecutor<ProgramCourse> {

	@Modifying
	@Query("UPDATE ProgramCourse p SET p.status = :status WHERE p.program.id = :programId")
	void updateStatusByProgramId(@Param("status") Status status, @Param("programId") Long programId);

	boolean existsByProgramIdAndCourseIdAndAcademicPeriod(Long programId, Long courseId, AcademicPeriod period);

	default Page<ProgramCourse> findAll(Program program, GetPrgCoursesPage request, Pageable pageable) {
		GeneralSpecification<ProgramCourse> spec = new GeneralSpecification<>();
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
