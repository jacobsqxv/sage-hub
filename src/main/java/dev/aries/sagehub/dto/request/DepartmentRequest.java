package dev.aries.sagehub.dto.request;

import java.util.List;

import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;

public record DepartmentRequest(
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		String name,
		List<Long> programIds,
		String status
) {
}
