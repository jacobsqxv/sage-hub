package dev.aries.sagehub.dto.request;

public record AdminRequest(
		String firstname,
		String middleName,
		String lastname,
		String primaryEmail,
		String profilePicture
) {
}
