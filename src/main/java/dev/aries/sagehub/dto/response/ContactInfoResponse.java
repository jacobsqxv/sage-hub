package dev.aries.sagehub.dto.response;

import dev.aries.sagehub.model.Address;
import lombok.Builder;

@Builder
public record ContactInfoResponse(
		Long id,
		String secondaryEmail,
		String phoneNumber,
		Address address,
		String postalAddress
) {
}
