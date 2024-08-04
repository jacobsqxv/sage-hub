package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.model.attribute.Username;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ResetPasswordRequest(
		@NotNull(message = "Username" + NOT_NULL)
		@Schema(example = "username")
		Username username
) {
}
