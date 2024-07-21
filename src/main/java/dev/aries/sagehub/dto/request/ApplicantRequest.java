package dev.aries.sagehub.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ApplicantRequest(
		@NotNull(message = "Academic year" + NOT_NULL)
		Integer applyingForYear,
		@NotNull(message = NOT_NULL + "Basic info")
		@Valid
		BasicInfoRequest basicInfo,
		@NotNull(message = NOT_NULL + "Contact info")
		@Valid
		ContactInfoRequest contactInfo,
		@NotNull(message = NOT_NULL + "Emergency contact")
		@Valid
		EmergencyContactRequest guardianInfo
) {
}
