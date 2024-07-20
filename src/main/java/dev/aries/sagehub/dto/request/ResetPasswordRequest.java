package dev.aries.sagehub.dto.request;

import jakarta.validation.constraints.NotEmpty;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ResetPasswordRequest(
		@NotEmpty(message = "Username" + NOT_NULL)
		String username
) {
}
