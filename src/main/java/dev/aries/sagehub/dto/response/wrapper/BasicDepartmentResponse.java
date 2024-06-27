package dev.aries.sagehub.dto.response.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record BasicDepartmentResponse(
		Long id,
		String code,
		String name,
		String programs,
		String status
) {
}
