package dev.aries.sagehub.strategy;

import dev.aries.sagehub.dto.request.EmergencyContactRequest;
import dev.aries.sagehub.model.EmergencyContact;

import org.springframework.stereotype.Component;

@Component
public class UpdateEmergencyContact implements UpdateStrategy<EmergencyContact, EmergencyContactRequest> {

	@Override
	public EmergencyContact update(EmergencyContact entity, EmergencyContactRequest request) {
		entity.setFullName(request.fullName() != null ? request.fullName() : entity.getFullName());
		entity.setPhoneNumber(request.phoneNumber() != null ? request.phoneNumber() : entity.getPhoneNumber());
		entity.setRelationship(request.relationship() != null ? request.relationship() : entity.getRelationship());
		entity.setAddress(request.address() != null ? request.address() : entity.getAddress());

		return entity;
	}
}
