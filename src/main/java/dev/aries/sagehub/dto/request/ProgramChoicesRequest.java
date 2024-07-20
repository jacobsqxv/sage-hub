package dev.aries.sagehub.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ProgramChoicesRequest(
		@Size(min = 4, max = 4, message = "There must be 4 program choices")
		@UniqueElements(message = "Program choices must be unique")
		@NotNull(message = "Program" + NOT_NULL)
		List<Long> programChoices
) {
}
