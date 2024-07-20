package dev.aries.sagehub.model.attribute;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;

public record Email(String value) {
	public Email {
		if (!value.matches(Patterns.EMAIL)) {
			throw new IllegalArgumentException(ValidationMessage.INVALID_FORMAT + "email");
		}
	}
}
