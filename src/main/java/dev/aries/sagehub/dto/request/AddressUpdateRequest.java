package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.Pattern;

public record AddressUpdateRequest(
		@Pattern(regexp = Patterns.STREET, message = ValidationMessage.STREET)
		String street,
		@Pattern(regexp = Patterns.CITY, message = ValidationMessage.CITY)
		String city,
		@Pattern(regexp = Patterns.REGION, message = ValidationMessage.REGION)
		String region,
		@Pattern(regexp = Patterns.NAME, message = ValidationMessage.NAME)
		String country
) {
}
