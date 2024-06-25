package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record PasswordResetRequest(
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		String username,
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		@Pattern(regexp = Patterns.PASSWORD, message = ValidationMessage.PASSWORD)
		String password,
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		String token
) {
}
