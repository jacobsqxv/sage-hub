package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record AddressUpdateRequest(
		@Pattern(regexp = Patterns.STREET, message = INVALID_FORMAT + "street")
		@NotEmpty(message = "Street" + NOT_NULL)
		@Schema(example = "1234 Ring Road")
		String street,
		@Pattern(regexp = Patterns.LOCATION, message = INVALID_FORMAT + "city")
		@NotEmpty(message = "City" + NOT_NULL)
		@Schema(example = "Accra")
		String city,
		@Pattern(regexp = Patterns.LOCATION, message = INVALID_FORMAT + "region")
		@NotEmpty(message = "Region" + NOT_NULL)
		@Schema(example = "Greater Accra")
		String region,
		@Pattern(regexp = Patterns.LOCATION, message = INVALID_FORMAT + "country")
		@NotEmpty(message = "Country" + NOT_NULL)
		@Schema(example = "Ghana")
		String country
) {
}
