package dev.aries.sagehub.dto.request;


import dev.aries.sagehub.constant.ValidationMessage;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import jakarta.validation.constraints.NotNull;

public record AuthRequest(
		@NotNull(message = "Username" + ValidationMessage.NOT_NULL)
		Username username,
		@NotNull(message = "Password" + ValidationMessage.NOT_NULL)
		Password password
) {
}
