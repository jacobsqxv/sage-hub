package dev.aries.sagehub.dto.search;

import lombok.Builder;

@Builder
public record GetProgramsPage(
		Integer page,
		Integer size,
		String name,
		String department,
		String status
) {
	public GetProgramsPage {
		page = (page != null) ? page : 1;
		size = (size != null) ? size : 10;
	}
}
