package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record VoucherRequest(
		@NotNull(message = ValidationMessage.NOT_NULL)
		Long serialNumber,
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		String pin
) {
}
