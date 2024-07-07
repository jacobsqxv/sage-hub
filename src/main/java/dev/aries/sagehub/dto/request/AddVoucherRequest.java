package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.Max;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VoucherRequest(
		@NotNull(message = ValidationMessage.NOT_NULL)
		Integer year,
		@Positive
		@Max(value = 100, message = ValidationMessage.MAX_VALUE)
		@NotNull(message = ValidationMessage.NOT_NULL)
		Integer quantity
) {
}
