package dev.aries.sagehub.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import static dev.aries.sagehub.constant.ValidationMessage.MAX_VALUE;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record AddVoucherRequest(
		@NotNull(message = "Academic year" + NOT_NULL)
		Integer year,
		@Positive
		@Max(value = 100, message = "Quantity" + MAX_VALUE)
		@NotNull(message = "Quantity" + NOT_NULL)
		Integer quantity
) {
}
