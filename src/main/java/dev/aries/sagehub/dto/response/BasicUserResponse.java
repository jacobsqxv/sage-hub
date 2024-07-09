package dev.aries.sagehub.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record BasicUserResponse(
		Long userId,
		Long memberId,
		String username,
		String primaryEmail,
		BasicInfoResponse basicInfo,
		String role,
		String status,
		String secondaryEmail
) {
}
