package dev.aries.sagehub.dto.response;

public record CourseOfferingResponse(
		Long id,
		CourseResponse course,
		String year,
		String semester,
		String status
) {
}
