package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.EmergencyContactResponse;
import dev.aries.sagehub.model.EmergencyContact;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmergencyContactMapper {

	public EmergencyContactResponse toEmergencyContactResponse(EmergencyContact emergencyContact) {
		return new EmergencyContactResponse(
				emergencyContact.getId(),
				emergencyContact.getFullName(),
				emergencyContact.getRelationship(),
				emergencyContact.getPhoneNumber(),
				emergencyContact.getAddress()
		);
	}

}
