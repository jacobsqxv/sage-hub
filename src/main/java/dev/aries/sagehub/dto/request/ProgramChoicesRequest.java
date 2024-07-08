package dev.aries.sagehub.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ProgramChoicesRequest(
		List<@NotNull(message = "Program" + NOT_NULL) Long> programIds
) {
}
