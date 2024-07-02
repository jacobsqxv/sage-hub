package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;

public record ResetPasswordRequest(
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		String username
) {
}
