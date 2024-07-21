package dev.aries.sagehub.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record UserResponse(
		Long userId,
		Long memberId,
		String role,
		String status,
		BasicInfoResponse basicInfo,
		ContactInfoResponse contactInfo,
		EmergencyContactResponse emergencyContact
) {
}
