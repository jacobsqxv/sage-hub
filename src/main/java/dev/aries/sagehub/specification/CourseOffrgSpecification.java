package dev.aries.sagehub.specification;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.enums.Semester;
import dev.aries.sagehub.enums.Year;
import dev.aries.sagehub.model.CourseOffering;
import dev.aries.sagehub.model.Program;

import org.springframework.data.jpa.domain.Specification;

public final class CourseOffrgSpecification {
	public static Specification<CourseOffering> hasCourse(String course) {
		return (root, query, criteriaBuilder) -> (course == null || course.trim().isEmpty()) ?
				null : criteriaBuilder.like(
				criteriaBuilder.lower(root.get("course").get("name")),
				"%" + course.trim().toLowerCase() + "%");
	}

	public static Specification<CourseOffering> hasProgram(Program program) {
		return (root, query, criteriaBuilder) -> (program != null) ?
				criteriaBuilder.equal(root.get("program"), program) : null;
	}

	public static Specification<CourseOffering> hasCreditUnits(Integer creditUnits) {
		return (root, query, criteriaBuilder) -> (creditUnits != null) ?
				criteriaBuilder.equal(root.get("course").get("creditUnits"), creditUnits) : null;
	}

	public static Specification<CourseOffering> hasYear(Year year) {
		return (root, query, criteriaBuilder) -> (year != null) ?
				criteriaBuilder.equal(root.get("academicPeriod").get("year"), year) : null;
	}

	public static Specification<CourseOffering> hasSemester(Semester semester) {
		return (root, query, criteriaBuilder) -> (semester != null) ?
				criteriaBuilder.equal(root.get("academicPeriod").get("semester"), semester) : null;
	}

	private CourseOffrgSpecification() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
