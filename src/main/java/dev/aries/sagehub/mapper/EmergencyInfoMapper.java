package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.EmergencyContactResponse;
import dev.aries.sagehub.model.EmergencyInfo;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmergencyContactMapper {

	public EmergencyContactResponse toEmergencyContactResponse(EmergencyInfo EmergencyInfo) {
		return new EmergencyContactResponse(
				EmergencyInfo.getId(),
				EmergencyInfo.getFullName(),
				EmergencyInfo.getRelationship(),
				EmergencyInfo.getPhoneNumber(),
				EmergencyInfo.getAddress()
		);
	}

}
