package dev.aries.sagehub.dto.request;

import java.util.List;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ProgramCourseRequest(
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		@Pattern(regexp = Patterns.NAME, message = ValidationMessage.NAME)
		String name,
		String description,
		@NotNull(message = ValidationMessage.NOT_NULL)
		Long departmentId,
		List<@NotNull Long> courseIds
) {
}
