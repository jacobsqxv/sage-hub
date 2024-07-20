package dev.aries.sagehub.dto.response;

import lombok.Builder;

@Builder
public record ProgramResponse(
		Long id,
		String name,
		String department,
		String degree,
		String description,
		Integer duration,
		Integer cutOff,
		String status
) {
}
