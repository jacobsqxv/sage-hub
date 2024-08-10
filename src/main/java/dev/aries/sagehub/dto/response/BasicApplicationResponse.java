package dev.aries.sagehub.dto.response;

import dev.aries.sagehub.enums.ApplicationStatus;

public record BasicApplicationResponse(
		Long applicationId,
		Long applicantId,
		Integer yearOfApplication,
		String profilePicture,
		String fullName,
		String email,
		ApplicationStatus status,
		boolean isSubmitted
) {
}
