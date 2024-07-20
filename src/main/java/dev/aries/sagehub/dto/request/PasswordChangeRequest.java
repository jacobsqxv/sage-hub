package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record PasswordChangeRequest(
		@NotEmpty(message = "Current password" + NOT_NULL)
		String oldPassword,
		@NotEmpty(message = "New password" + NOT_NULL)
		@Pattern(regexp = Patterns.PASSWORD, message = INVALID_FORMAT + "new password")
		String newPassword
) {
}
