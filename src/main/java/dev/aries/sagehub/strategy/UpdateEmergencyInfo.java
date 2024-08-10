package dev.aries.sagehub.strategy;

import java.util.Optional;

import dev.aries.sagehub.dto.request.EmergencyInfoRequest;
import dev.aries.sagehub.model.EmergencyInfo;

import org.springframework.stereotype.Component;

@Component
public class UpdateEmergencyInfo implements UpdateStrategy<EmergencyInfo, EmergencyInfoRequest> {

	@Override
	public EmergencyInfo update(EmergencyInfo entity, EmergencyInfoRequest request) {
		Optional.ofNullable(request.fullName()).ifPresent(entity::setFullName);
		Optional.ofNullable(request.phoneNumber().number()).ifPresent(entity::setPhoneNumber);
		Optional.ofNullable(request.relationship()).ifPresent(entity::setRelationship);
		Optional.ofNullable(request.email().value()).ifPresent(entity::setEmail);
		Optional.ofNullable(request.occupation()).ifPresent(entity::setOccupation);
		entity.setAddress(
				UpdateAttributes.updateAddress(entity.getAddress(), request.address()));
		return entity;
	}
}
