package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.model.attribute.PhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ContactInfoRequest(
		@NotNull(message = "Email" + NOT_NULL)
		@Schema(example = "john.doe2@example.com")
		Email secondaryEmail,
		@NotNull(message = "Phone number" + NOT_NULL)
		@Schema(example = "{\"countryCode\":\"GH\",\"number\":\"500000000\"}")
		PhoneNumber phoneNumber,
		@Valid
		@Schema(implementation = AddressRequest.class)
		AddressRequest residentialAddress,
		@Pattern(regexp = Patterns.POSTAL_ADDRESS, message = INVALID_FORMAT + "postal address")
		@Schema(example = "P.O. Box 1234, Accra")
		String postalAddress
) {
}
