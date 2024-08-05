package dev.aries.sagehub.strategy;

import dev.aries.sagehub.dto.request.ContactInfoRequest;
import dev.aries.sagehub.model.ContactInfo;

import org.springframework.stereotype.Component;

import static dev.aries.sagehub.strategy.UpdateAddress.updateAddress;

@Component
public class UpdateContactInfo implements UpdateStrategy<ContactInfo, ContactInfoRequest> {

	@Override
	public ContactInfo update(ContactInfo entity, ContactInfoRequest request) {
		entity.setSecondaryEmail((request.secondaryEmail() != null) ? request.secondaryEmail().value() : entity
				.getSecondaryEmail());
		entity.setPhoneNumber((request.phoneNumber() != null) ?
				request.phoneNumber().number() : entity.getPhoneNumber());
		entity.setAddress(updateAddress(entity.getAddress(), request.address()));
		entity.setPostalAddress((request.postalAddress() != null) ?
				request.postalAddress() : entity.getPostalAddress());

		return entity;
	}
}
