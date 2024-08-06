package dev.aries.sagehub.dto.response;

import dev.aries.sagehub.enums.ApplicantStatus;

public record BasicApplicantResponse(
		Long userId,
		Long applicantId,
		Integer yearOfApplication,
		String profilePicture,
		String fullName,
		String email,
		ApplicantStatus status,
		boolean isSubmitted
) {
}
