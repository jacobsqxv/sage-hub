package dev.aries.sagehub.model.attribute;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Name(
		@Column(nullable = false)
		String firstName,
		String middleName,
		@Column(nullable = false)
		String lastName
) {
	public String fullName() {
		return getFullName(firstName, middleName, lastName);
	}

	private static String getFullName(String firstName, String middleName, String lastName) {
		StringBuilder builder = new StringBuilder();
		builder.append(firstName).append(" ");
		if (middleName == null || middleName.isEmpty()) {
			return builder.append(lastName).toString().trim();
		}
		builder.append(middleName).append(" ");
		builder.append(lastName);
		return builder.toString().trim();
	}
}
