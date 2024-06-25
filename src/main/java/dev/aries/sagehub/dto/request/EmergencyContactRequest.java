package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EmergencyContactRequest(
		@Pattern(regexp = Patterns.NAME, message = ValidationMessage.NAME)
		String fullName,
		@NotNull(message = ValidationMessage.NOT_NULL)
		String relationship,
		@Pattern(regexp = Patterns.PHONE_NUMBER, message = ValidationMessage.PHONE_NUMBER)
		String phoneNumber,
		@Pattern(regexp = Patterns.ADDRESS, message = ValidationMessage.ADDRESS)
		String address
) {
}
