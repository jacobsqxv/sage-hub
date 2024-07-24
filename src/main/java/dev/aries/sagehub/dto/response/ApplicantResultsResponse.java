package dev.aries.sagehub.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record ApplicantResultsResponse(
		Long id,
		String schoolName,
		String type,
		Integer year,
		String indexNumber,
		List<SubjectScoreResponse> scores
) {
}
