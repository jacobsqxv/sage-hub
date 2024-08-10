package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record NameRequest(
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "first name")
		@NotEmpty(message = "First name" + NOT_NULL)
		@Schema(example = "John")
		String firstName,
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "middle name")
		@Schema(example = "Mike")
		String middleName,
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "last name")
		@NotEmpty(message = "Last name" + NOT_NULL)
		@Schema(example = "Doe")
		String lastName
) {
}
