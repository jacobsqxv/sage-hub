package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record AddressUpdateRequest(
		@Pattern(regexp = Patterns.STREET, message = INVALID_FORMAT + "street")
		@NotEmpty(message = "Street" + NOT_NULL + "street")
		String street,
		@Pattern(regexp = Patterns.CITY, message = INVALID_FORMAT + "city")
		@NotEmpty(message = "City" + NOT_NULL + "city")
		String city,
		@Pattern(regexp = Patterns.REGION, message = INVALID_FORMAT + "region")
		@NotEmpty(message = "Region" + NOT_NULL + "region")
		String region,
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "country")
		@NotEmpty(message = "Country" + NOT_NULL + "country")
		String country
) {
}
