package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record AuthRequest(
		@NotEmpty(message = "Username" + ValidationMessage.NOT_NULL)
		String username,
		@NotEmpty(message = "Password" + ValidationMessage.NOT_NULL)
		String password
) {
}
