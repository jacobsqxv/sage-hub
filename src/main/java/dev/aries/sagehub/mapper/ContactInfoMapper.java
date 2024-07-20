package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.ContactInfoResponse;
import dev.aries.sagehub.model.ContactInfo;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactInfoMapper {

	public ContactInfoResponse toContactInfoResponse(ContactInfo contactInfo) {
		return new ContactInfoResponse(
				contactInfo.getId(),
				contactInfo.getSecondaryEmail(),
				contactInfo.getPhoneNumber(),
				contactInfo.getAddress(),
				contactInfo.getPostalAddress()
		);
	}

}
