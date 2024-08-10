package dev.aries.sagehub.enums;

/**
 * The {@code Gender} class represents the different genders. It is used throughout the
 * application wherever gender specification is required.
 * It includes the following values:
 * <ul>
 * <li>{@code MALE} - Represents the male gender.</li>
 * <li>{@code FEMALE} - Represents the female gender.</li>
 * </ul>
 *
 * @author Jacobs Agyei
 */
public enum Gender {

	/**
	 * Represents the male gender.
	 */
	MALE,
	/**
	 * Represents the female gender.
	 */
	FEMALE;

	@Override
	public String toString() {
		return switch (this) {
			case MALE -> "Male";
			case FEMALE -> "Female";
		};
	}
}
