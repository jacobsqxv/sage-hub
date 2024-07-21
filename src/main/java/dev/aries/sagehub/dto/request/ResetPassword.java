package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record ResetPassword(
		@NotEmpty(message = "Username" + ValidationMessage.NOT_NULL)
		Username username,
		@NotEmpty(message = "Password" + ValidationMessage.NOT_NULL)
		Password password
) {
}
