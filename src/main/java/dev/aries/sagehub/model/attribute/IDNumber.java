package dev.aries.sagehub.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.persistence.Embeddable;

@Embeddable
public record IDNumber(String value) {
	public IDNumber {
		if (!value.matches(Patterns.ID)) {
			throw new IllegalArgumentException(ValidationMessage.INVALID_FORMAT + "ID number");
		}
	}

	@JsonCreator
	public static IDNumber of(String value) {
		return new IDNumber(value);
	}
}
