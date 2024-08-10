package dev.aries.sagehub.mapper;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.response.CourseResponse;
import dev.aries.sagehub.dto.response.CrseOffrgResponse;
import dev.aries.sagehub.model.Course;
import dev.aries.sagehub.model.CourseOffering;

public final class CourseMapper {

	public static CourseResponse toCourseResponse(Course course) {
		return new CourseResponse(
				course.getId(),
				course.getCode(),
				course.getName(),
				course.getDescription(),
				course.getCreditUnits(),
				course.getStatus().toString()
		);
	}

	public static CrseOffrgResponse toCourseOffrgResponse(CourseOffering courseOffering) {
		return new CrseOffrgResponse(
				courseOffering.getId(),
				toCourseResponse(courseOffering.getCourse()),
				courseOffering.getAcademicPeriod().year().toString(),
				courseOffering.getAcademicPeriod().semester().toString(),
				courseOffering.getStatus().toString()
		);
	}

	private CourseMapper() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
