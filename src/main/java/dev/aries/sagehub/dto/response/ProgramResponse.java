package dev.aries.sagehub.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ProgramResponse(
		Long id,
		String name,
		String department,
		String degree,
		String description,
		Integer duration,
		String status
) {
}
