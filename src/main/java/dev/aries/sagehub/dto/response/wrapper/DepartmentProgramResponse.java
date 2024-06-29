package dev.aries.sagehub.dto.response.wrapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.aries.sagehub.dto.response.ProgramResponse;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record DepartmentProgramResponse(
		Long id,
		String code,
		String name,
		List<ProgramResponse> programs,
		String status
) {
}
