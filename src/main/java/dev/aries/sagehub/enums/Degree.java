package dev.aries.sagehub.enums;

/**
 * The {@code Degree} enum represents the different levels of academic degrees
 * that a program can offer. It is used to categorize programs by their academic level.
 * @author Jacobs Agyei
 */
public enum Degree {
	/**
	 * Represents an undergraduate program leading to a bachelor's degree.
	 */
	BACHELORS,
	/**
	 * Represents a graduate program leading to a master's degree.
	 */
	MASTERS,
	/**
	 * Represents a postgraduate program leading to a doctorate degree.
	 */
	DOCTORATE;

	@Override
	public String toString() {
		return switch (this) {
			case BACHELORS -> "Bachelors";
			case MASTERS -> "Masters";
			case DOCTORATE -> "Doctorate";
		};
	}
}
