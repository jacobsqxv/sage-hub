package dev.aries.sagehub.dto.search;

import dev.aries.sagehub.enums.Semester;
import dev.aries.sagehub.enums.Year;

public record GetPrgCoursesPage(
		String course,
		Integer creditUnits,
		Year year,
		Semester semester,
		String status
) {
	public GetPrgCoursesPage withStatus(String status) {
		return new GetPrgCoursesPage(course, creditUnits, year, semester, status);
	}
}
