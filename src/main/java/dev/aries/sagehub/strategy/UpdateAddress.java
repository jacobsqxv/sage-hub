package dev.aries.sagehub.strategy;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.AddressUpdateRequest;
import dev.aries.sagehub.model.Address;

public final class UpdateAddress {
	public static Address updateAddress(Address address, AddressUpdateRequest request) {
		return new Address(
				(request.street() != null) ? request.street() : address.street(),
				(request.city() != null) ? request.city() : address.city(),
				(request.region() != null) ? request.region() : address.region(),
				(request.country() != null) ? request.country() : address.country()
		);
	}
	private UpdateAddress() {
		throw new IllegalArgumentException(ExceptionConstants.UTILITY_CLASS);
	}
}
