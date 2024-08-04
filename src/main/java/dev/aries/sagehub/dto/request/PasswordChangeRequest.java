package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.model.attribute.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record PasswordChangeRequest(
		@NotNull(message = "Current password" + NOT_NULL)
		@Schema(example = "P@ssw0rd")
		Password oldPassword,
		@NotNull(message = "New password" + NOT_NULL)
		@Schema(example = "N3wP@ssw0rd")
		Password newPassword
) {
}
