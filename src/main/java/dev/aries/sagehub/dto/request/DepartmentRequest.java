package dev.aries.sagehub.dto.request;

import java.util.List;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record DepartmentRequest(
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "department name")
		@NotEmpty(message = "Department name" + NOT_NULL)
		@Schema(example = "Computer Science")
		String name,
		List<Long> programIds,
		@Schema(example = "ACTIVE")
		Status status
) {
}
