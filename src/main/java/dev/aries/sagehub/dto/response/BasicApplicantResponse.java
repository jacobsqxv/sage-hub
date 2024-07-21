package dev.aries.sagehub.dto.response;

public record BasicApplicantResponse(
		Long applicantId,
		Integer applyingForYear,
		BasicInfoResponse basicInfo,
		String status,
		boolean isSubmitted
) {
}
