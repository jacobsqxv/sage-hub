package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.model.attribute.Password;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record PasswordChangeRequest(
		@NotNull(message = "Current password" + NOT_NULL)
		Password oldPassword,
		@NotNull(message = "New password" + NOT_NULL)
		Password newPassword
) {
}
