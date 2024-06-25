package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.ContactInfoResponse;
import dev.aries.sagehub.model.ContactInfo;

import org.springframework.stereotype.Component;

@Component
public class ContactInfoMapper {

	public ContactInfoResponse toContactInfoResponse(ContactInfo contactInfo) {
		return new ContactInfoResponse(
				contactInfo.getId(),
				contactInfo.getSecondaryEmail(),
				contactInfo.getPhoneNumber(),
				contactInfo.getAddress(),
				contactInfo.getCity(),
				contactInfo.getRegion()
		);
	}

}