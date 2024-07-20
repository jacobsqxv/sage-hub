package dev.aries.sagehub.dto.response;

public record BasicApplicationResponse(
		Long id,
		Long applicantId,
		Integer applyingForYear,
		BasicInfoResponse basicInfo,
		String status,
		boolean isSubmitted
) {
}
