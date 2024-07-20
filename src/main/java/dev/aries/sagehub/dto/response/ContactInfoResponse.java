package dev.aries.sagehub.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.aries.sagehub.model.Address;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ContactInfoResponse(
		Long id,
		String secondaryEmail,
		String phoneNumber,
		Address address,
		String postalAddress
) {
}
