package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.request.ContactInfoRequest;
import dev.aries.sagehub.dto.response.ContactInfoResponse;
import dev.aries.sagehub.model.Address;
import dev.aries.sagehub.model.ContactInfo;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactInfoMapper {
	private final AddressMapper addressMapper;

	public ContactInfoResponse toContactInfoResponse(ContactInfo contactInfo) {
		return new ContactInfoResponse(
				contactInfo.getId(),
				contactInfo.getSecondaryEmail(),
				contactInfo.getPhoneNumber(),
				contactInfo.getAddress(),
				contactInfo.getPostalAddress()
		);
	}

	public ContactInfo toContactInfo(ContactInfoRequest contactInfoRequest) {
		Address address = this.addressMapper.toAddress(contactInfoRequest.address());
		return ContactInfo.builder()
				.secondaryEmail(contactInfoRequest.secondaryEmail())
				.phoneNumber(contactInfoRequest.phoneNumber())
				.address(address)
				.postalAddress(contactInfoRequest.postalAddress())
				.build();
	}
}
