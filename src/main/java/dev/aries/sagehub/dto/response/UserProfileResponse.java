package dev.aries.sagehub.dto.response;

import dev.aries.sagehub.model.attribute.ContactInfo;
import dev.aries.sagehub.model.attribute.PersonalInfo;
import lombok.Builder;

@Builder
public record UserProfileResponse(
		PersonalInfo personalInfo,
		ContactInfo contactInfo
) {
}
