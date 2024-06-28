package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.CourseResponse;
import dev.aries.sagehub.model.Course;

import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

	public CourseResponse toCourseResponse(Course course) {
		return new CourseResponse(
				course.getId(),
				course.getCode(),
				course.getName(),
				course.getDescription(),
				course.getCreditUnits(),
				course.getStatus().getValue()
		);
	}
}
