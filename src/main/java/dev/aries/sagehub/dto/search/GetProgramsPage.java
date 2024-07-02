package dev.aries.sagehub.dto.search;

import lombok.Builder;

@Builder
public record GetProgramsPage(
		String name,
		String department,
		String status
) {
}
