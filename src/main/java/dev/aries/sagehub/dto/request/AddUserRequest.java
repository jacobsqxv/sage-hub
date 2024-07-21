package dev.aries.sagehub.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record AddUserRequest(
		@NotNull(message = "Basic info" + NOT_NULL)
		@Valid
		BasicInfoRequest basicInfo,
		@NotNull(message = "Contact info"  + NOT_NULL)
		@Valid
		ContactInfoRequest contactInfo,
		@NotNull(message = "Emergency contact"  + NOT_NULL)
		@Valid
		EmergencyContactRequest emergencyContact
) {
}
