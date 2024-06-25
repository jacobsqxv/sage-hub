package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.Pattern;

public record ContactInfoRequest(
		@Pattern(regexp = Patterns.EMAIL, message = ValidationMessage.EMAIL)
		String secondaryEmail,
		@Pattern(regexp = Patterns.PHONE_NUMBER, message = ValidationMessage.PHONE_NUMBER)
		String phoneNumber,
		@Pattern(regexp = Patterns.ADDRESS, message = ValidationMessage.ADDRESS)
		String address,
		@Pattern(regexp = Patterns.CITY, message = ValidationMessage.CITY)
		String city,
		@Pattern(regexp = Patterns.REGION, message = ValidationMessage.REGION)
		String region
) {
}