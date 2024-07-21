package dev.aries.sagehub.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.persistence.Embeddable;

@Embeddable
public record Email(String value) {
	public Email {
		if (!value.matches(Patterns.EMAIL)) {
			throw new IllegalArgumentException(ValidationMessage.INVALID_FORMAT + "email");
		}
	}

	@JsonCreator
	public static Email of(String value) {
		return new Email(value);
	}
}
