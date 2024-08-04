package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.ValidationMessage;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ResetPassword(
		@NotNull(message = "Username" + ValidationMessage.NOT_NULL)
		@Schema(example = "username")
		Username username,
		@NotNull(message = "Password" + ValidationMessage.NOT_NULL)
		@Schema(example = "N3wP@ssw0rd")
		Password password
) {
}
