package dev.aries.sagehub.dto.response;

public record ProgramCourseResponse(
		Long id,
		ProgramResponse program,
		CourseResponse course,
		String year,
		String semester,
		String status
) {
}
