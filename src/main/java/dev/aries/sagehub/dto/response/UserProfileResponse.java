package dev.aries.sagehub.dto.response;

import dev.aries.sagehub.model.attribute.ContactInfo;
import dev.aries.sagehub.model.attribute.PersonalInfo;
import lombok.Builder;

@Builder
public record UserInfoResponse(
		Long id,
		PersonalInfo personalInfo,
		ContactInfo contactInfo
) {
}
