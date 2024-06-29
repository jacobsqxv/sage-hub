package dev.aries.sagehub.dto.search;

import lombok.Builder;

@Builder
public record GetDepartmentsPage(
		Integer page,
		Integer size,
		String name,
		String code,
		String status
) {
	public GetDepartmentsPage {
		page = (page != null) ? page : 1;
		size = (size != null) ? size : 10;
	}
}
