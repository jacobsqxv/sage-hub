package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ProgramRequest(
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "program name")
		@NotEmpty(message = "Program name" + NOT_NULL)
		String name,
		String description,
		@NotNull(message = "Department ID" + NOT_NULL)
		Long departmentId,
		String status
) {
}
