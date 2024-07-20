package dev.aries.sagehub.dto.response;

import lombok.Builder;

@Builder
public record SubjectScoreResponse(
		String subject,
		Character grade
) {
}
