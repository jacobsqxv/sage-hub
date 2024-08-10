package dev.aries.sagehub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

public record UserProfileRequest(
	@Valid
	@Schema(implementation = PersonalInfoRequest.class)
	PersonalInfoRequest personalInfo,
	@Valid
	@Schema(implementation = ContactInfoRequest.class)
	ContactInfoRequest contactInfo
) {
}
