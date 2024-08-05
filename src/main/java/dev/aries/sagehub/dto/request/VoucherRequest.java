package dev.aries.sagehub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record VoucherRequest(
		@NotNull(message = NOT_NULL)
		@Schema(example = "59092349")
		Long serialNumber,
		@NotEmpty(message = "PIN" + NOT_NULL)
		@Schema(example = "P@ssw0rd")
		String pin
) {
}
