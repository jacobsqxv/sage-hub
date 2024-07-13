package dev.aries.sagehub.enums;

/**
 * Gender is an enumeration representing the different genders. It is used throughout the
 * application wherever gender specification is required.
 *
 * <p>
 * It includes the following values:
 * <ul>
 * <li>MALE: Represents the male gender.</li>
 * <li>FEMALE: Represents the female gender.</li>
 * </ul>
 * </p>
 *
 * @author Aries N/A
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
