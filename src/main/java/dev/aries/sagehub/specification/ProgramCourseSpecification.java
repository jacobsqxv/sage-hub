package dev.aries.sagehub.specification;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.enums.Semester;
import dev.aries.sagehub.enums.Year;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.model.ProgramCourse;

import org.springframework.data.jpa.domain.Specification;

public final class ProgramCourseSpecification {
	public static Specification<ProgramCourse> hasCourse(String course) {
		return (root, query, criteriaBuilder) -> (course == null || course.trim().isEmpty()) ?
				null : criteriaBuilder.like(
				criteriaBuilder.lower(root.get("course").get("name")),
				"%" + course.trim().toLowerCase() + "%");
	}

	public static Specification<ProgramCourse> hasProgram(Program program) {
		return (root, query, criteriaBuilder) -> (program != null) ?
				criteriaBuilder.equal(root.get("program"), program) : null;
	}

	public static Specification<ProgramCourse> hasCreditUnits(Integer creditUnits) {
		return (root, query, criteriaBuilder) -> (creditUnits != null) ?
				criteriaBuilder.equal(root.get("course").get("creditUnits"), creditUnits) : null;
	}

	public static Specification<ProgramCourse> hasYear(Year year) {
		return (root, query, criteriaBuilder) -> (year != null) ?
				criteriaBuilder.equal(root.get("academicPeriod").get("year"), year) : null;
	}

	public static Specification<ProgramCourse> hasSemester(Semester semester) {
		return (root, query, criteriaBuilder) -> (semester != null) ?
				criteriaBuilder.equal(root.get("academicPeriod").get("semester"), semester) : null;
	}

	private ProgramCourseSpecification() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
