package dev.aries.sagehub.dto.search;

import dev.aries.sagehub.enums.Semester;
import dev.aries.sagehub.enums.Year;

public record GetCrseOffrgPage(
		String course,
		Integer creditUnits,
		Year year,
		Semester semester,
		String status
) {
	public GetCrseOffrgPage withStatus(String status) {
		return new GetCrseOffrgPage(course, creditUnits, year, semester, status);
	}
}
