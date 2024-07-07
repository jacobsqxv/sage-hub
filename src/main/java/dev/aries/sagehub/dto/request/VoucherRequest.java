package dev.aries.sagehub.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record VoucherRequest(
		@NotNull(message = NOT_NULL)
		Long serialNumber,
		@NotEmpty(message = "PIN" + NOT_NULL)
		String pin
) {
}
