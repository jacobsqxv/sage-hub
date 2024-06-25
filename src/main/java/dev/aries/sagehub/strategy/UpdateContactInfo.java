package dev.aries.sagehub.strategy;

import dev.aries.sagehub.dto.request.ContactInfoRequest;
import dev.aries.sagehub.dto.request.EmergencyContactRequest;
import dev.aries.sagehub.model.ContactInfo;
import dev.aries.sagehub.model.EmergencyContact;

import org.springframework.stereotype.Component;

@Component
public class UpdateContactInfo implements UpdateStrategy<ContactInfo, ContactInfoRequest> {

	@Override
	public ContactInfo update(ContactInfo entity, ContactInfoRequest request) {
		entity.setSecondaryEmail(request.secondaryEmail() != null ? request.secondaryEmail() : entity
			.getSecondaryEmail());
		entity.setPhoneNumber(request.phoneNumber() != null ? request.phoneNumber() : entity.getPhoneNumber());
		entity.setAddress(request.address() != null ? request.address() : entity.getAddress());
		entity.setCity(request.city() != null ? request.city() : entity.getCity());
		entity.setRegion(request.region() != null ? request.region() : entity.getRegion());

		return entity;
	}
}
