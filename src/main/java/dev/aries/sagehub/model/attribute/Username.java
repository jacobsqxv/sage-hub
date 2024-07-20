package dev.aries.sagehub.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.aries.sagehub.constant.ValidationMessage;

public record Username(String value) {
	public Username {
		if (value.isEmpty() || value.isBlank()) {
			throw new IllegalArgumentException(ValidationMessage.NOT_EMPTY + "Username");
		}
	}

	@JsonCreator
	public static Username of(String value) {
		return new Username(value);
	}
}
