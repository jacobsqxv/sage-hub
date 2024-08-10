package dev.aries.sagehub.dto.response;

import dev.aries.sagehub.model.attribute.Address;
import lombok.Builder;

@Builder
public record EmergencyContactResponse(
		Long id,
		String fullName,
		String relationship,
		String phoneNumber,
		Address address
) {
}
