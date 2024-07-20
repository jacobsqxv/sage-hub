package dev.aries.sagehub.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record DepartmentResponse(
		Long id,
		String code,
		String name,
		List<ProgramResponse> programs,
		String status
) {
}
