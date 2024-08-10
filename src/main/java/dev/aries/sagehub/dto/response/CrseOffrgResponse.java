package dev.aries.sagehub.dto.response;

public record CrseOffrgResponse(
		Long id,
		CourseResponse course,
		String year,
		String semester,
		String status
) {
}
