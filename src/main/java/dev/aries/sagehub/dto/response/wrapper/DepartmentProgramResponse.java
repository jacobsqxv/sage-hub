package dev.aries.sagehub.dto.response.wrapper;

import java.util.List;

import dev.aries.sagehub.dto.response.ProgramCourseResponse;

public record DepartmentProgramResponse(
		Long id,
		String code,
		String name,
		List<ProgramCourseResponse> programs,
		String status
) {
}
