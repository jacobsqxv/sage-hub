package dev.aries.sagehub.dto.search;

import lombok.Builder;

@Builder
public record GetCoursesPage(
		Integer page,
		Integer size,
		String name,
		String code,
		String status
) {
	public GetCoursesPage {
		page = (page != null) ? page : 1;
		size = (size != null) ? size : 10;
	}
}
