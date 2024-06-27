package dev.aries.sagehub.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record CourseResponse(
		Long id,
		String code,
		String name,
		String description,
		Integer creditUnits,
		String status
) {
}
