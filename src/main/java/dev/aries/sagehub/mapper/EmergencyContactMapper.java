package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.request.EmergencyContactRequest;
import dev.aries.sagehub.dto.response.EmergencyContactResponse;
import dev.aries.sagehub.model.Address;
import dev.aries.sagehub.model.EmergencyContact;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmergencyContactMapper {
	private final AddressMapper addressMapper;

	public EmergencyContactResponse toEmergencyContactResponse(EmergencyContact emergencyContact) {
		return new EmergencyContactResponse(
				emergencyContact.getId(),
				emergencyContact.getFullName(),
				emergencyContact.getRelationship(),
				emergencyContact.getPhoneNumber(),
				emergencyContact.getAddress()
		);
	}

	public EmergencyContact toEmergencyContact(EmergencyContactRequest emergencyContactRequest) {
		Address address = this.addressMapper.toAddress(emergencyContactRequest.address());
		return EmergencyContact.builder()
				.fullName(emergencyContactRequest.fullName())
				.relationship(emergencyContactRequest.relationship().toUpperCase())
				.phoneNumber(emergencyContactRequest.phoneNumber())
				.address(address)
				.build();
	}
}
