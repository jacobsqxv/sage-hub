package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.request.AddressUpdateRequest;
import dev.aries.sagehub.model.Address;

import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
	public Address toAddress(AddressUpdateRequest request) {
		return new Address(
				request.street(),
				request.city(),
				request.region(),
				request.country()
		);
	}
}
