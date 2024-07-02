package dev.aries.sagehub.dto.search;

import lombok.Builder;

@Builder
public record GetCoursesPage(
		String name,
		String code,
		String status
) {
}
