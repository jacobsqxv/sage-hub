package dev.aries.sagehub.dto.search;

import dev.aries.sagehub.enums.Semester;
import dev.aries.sagehub.enums.Year;

public record GetCourseOffgsPage(
		String course,
		Integer creditUnits,
		Year year,
		Semester semester,
		String status
) {
	public GetCourseOffgsPage withStatus(String status) {
		return new GetCourseOffgsPage(course, creditUnits, year, semester, status);
	}
}
