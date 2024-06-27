package dev.aries.sagehub.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record EmergencyContactResponse(
		Long id,
		String fullName,
		String relationship,
		String phoneNumber,
		String address
) {
}
