package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record PasswordChangeRequest(
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		String oldPassword,
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		@Pattern(regexp = Patterns.PASSWORD, message = ValidationMessage.PASSWORD)
		String newPassword
) {
}
