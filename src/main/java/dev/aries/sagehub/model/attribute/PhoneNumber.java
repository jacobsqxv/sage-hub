package dev.aries.sagehub.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.persistence.Embeddable;

@Embeddable
public record PhoneNumber(String value) {
	public PhoneNumber {
		if (!value.matches(Patterns.PHONE_NUMBER)) {
			throw new IllegalArgumentException(ValidationMessage.INVALID_FORMAT + "phone number");
		}
	}

	@JsonCreator
	public static PhoneNumber of(String value) {
		return new PhoneNumber(value);
	}
}
