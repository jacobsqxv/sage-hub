package dev.aries.sagehub.dto.response;

import lombok.Builder;

@Builder
public record CourseResponse(
		Long id,
		String code,
		String name,
		String description,
		Integer creditUnits,
		String status
) {
}
