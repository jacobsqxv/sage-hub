package dev.aries.sagehub.dto.response;

public record ProgramCourseResponse(
		Long id,
		CourseResponse course,
		String year,
		String semester,
		String status
) {
}
