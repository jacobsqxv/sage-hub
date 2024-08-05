package dev.aries.sagehub.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.aries.sagehub.constant.ValidationMessage;
import dev.aries.sagehub.util.Checks;
import jakarta.persistence.Embeddable;

@Embeddable
public record PhoneNumber(String countryCode, String number) {
	public PhoneNumber {
		if (!Checks.isValidPhoneNumber(number, countryCode)) {
			throw new IllegalArgumentException(ValidationMessage.INVALID_FORMAT + "phone number");
		}
	}

	@JsonCreator
	public static PhoneNumber of(String countryCode, String number) {
		return new PhoneNumber(countryCode, number);
	}
}
