package dev.aries.sagehub.strategy;

import java.util.Optional;

import dev.aries.sagehub.dto.request.EmergencyContactRequest;
import dev.aries.sagehub.model.EmergencyContact;

import org.springframework.stereotype.Component;

import static dev.aries.sagehub.strategy.UpdateAddress.updateAddress;

@Component
public class UpdateEmergencyContact implements UpdateStrategy<EmergencyContact, EmergencyContactRequest> {

	@Override
	public EmergencyContact update(EmergencyContact entity, EmergencyContactRequest request) {
		Optional.ofNullable(request.fullName()).ifPresent(entity::setFullName);
		Optional.ofNullable(request.phoneNumber().value()).ifPresent(entity::setPhoneNumber);
		Optional.ofNullable(request.relationship()).ifPresent(entity::setRelationship);
		Optional.ofNullable(request.email().value()).ifPresent(entity::setEmail);
		Optional.ofNullable(request.occupation()).ifPresent(entity::setOccupation);
		entity.setAddress(updateAddress(entity.getAddress(), request.address()));
		return entity;
	}
}
