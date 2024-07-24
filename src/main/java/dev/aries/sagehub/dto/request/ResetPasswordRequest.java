package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.model.attribute.Username;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ResetPasswordRequest(
		@NotNull(message = "Username" + NOT_NULL)
		Username username
) {
}
