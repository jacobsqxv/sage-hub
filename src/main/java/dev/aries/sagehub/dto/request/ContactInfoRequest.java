package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.model.attribute.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ContactInfoRequest(
		@NotNull(message = "Email" + NOT_NULL)
		Email secondaryEmail,
		@NotNull(message = "Phone number" + NOT_NULL)
		PhoneNumber phoneNumber,
		@Valid
		AddressUpdateRequest address,
		@Pattern(regexp = Patterns.POSTAL_ADDRESS, message = INVALID_FORMAT + "postal address")
		String postalAddress
) {
}
