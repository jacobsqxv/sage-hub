package dev.aries.sagehub.dto.response;

import java.util.List;

public record ProgramCourseResponse(
		ProgramResponse program,
		List<CourseResponse> courses,
		String year,
		String semester,
		String status
) {
}
