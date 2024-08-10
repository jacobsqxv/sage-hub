package dev.aries.sagehub.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record ExamResultResponse(
		Long id,
		String type,
		Integer year,
		String indexNumber,
		List<SubjectScoreResponse> scores
) {
}
