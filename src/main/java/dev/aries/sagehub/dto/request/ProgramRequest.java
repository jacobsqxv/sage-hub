package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.enums.Degree;
import dev.aries.sagehub.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.GREATER_THAN;
import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.LESS_THAN;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ProgramRequest(
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "program name")
		@NotEmpty(message = "Program name" + NOT_NULL)
		@Schema(example = "Computer Science")
		String name,
		@NotNull(message = "Degree offered" + NOT_NULL)
		@Schema(example = "BACHELORS")
		Degree degree,
		@Schema(example = "An undergraduate program in computer science")
		String description,
		@NotNull(message = "Department ID" + NOT_NULL)
		@Schema(example = "1")
		Long departmentId,
		@NotNull(message = "Cut off" + NOT_NULL)
		@Max(value = 30, message = "Cut off" + LESS_THAN + "30")
		@Min(value = 6, message = "Cut off" + GREATER_THAN + "6")
		@Schema(example = "10")
		Integer cutOff,
		@NotNull(message = "Program duration" + NOT_NULL)
		@Max(value = 6, message = "Program duration" + LESS_THAN + "5")
		@Min(value = 2, message = "Program duration" + GREATER_THAN + "2")
		@Schema(example = "4")
		Integer duration,
		@Schema(example = "ACTIVE")
		Status status
) {
}
