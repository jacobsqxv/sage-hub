package dev.aries.sagehub.dto.request;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;

@Validated
public record ApplicationRequest(
		@Valid
		BasicInfoRequest basicInfo,
		@Valid
		ContactInfoRequest contactInfo,
		@Valid
		EmergencyContactRequest guardianInfo,
		List<Long> resultIds,
		List<Long> programIds,
		String status,
		boolean isSubmitted,
		Integer applyingForYearId
) {
}
