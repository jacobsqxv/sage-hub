package dev.aries.sagehub.dto.search;

import lombok.Builder;

@Builder
public record GetDepartmentsPage(
		String name,
		String code,
		String status
) {
}
