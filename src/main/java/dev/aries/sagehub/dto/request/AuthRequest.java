package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;

public record AuthRequest(
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		String username,
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		String password
) {
}
