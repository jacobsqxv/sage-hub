package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record ProgramRequest(
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		@Pattern(regexp = Patterns.NAME, message = ValidationMessage.NAME)
		String name,
		String description,
		String status
) {
}
