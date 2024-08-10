package dev.aries.sagehub.enums;

/**
 * The {@code Degree} class represents the different levels of academic degrees
 * that a program can offer. It is used to categorize programs by their academic level.
 * <ul>
 *     <li>{@code BACHELORS} - Represents an undergraduate program leading to a bachelor's degree.</li>
 *     <li>{@code MASTERS} - Represents a graduate program leading to a master's degree.</li>
 *     <li>{@code DOCTORATE} - Represents a postgraduate program leading to a doctorate degree.</li>
 * </ul>
 *
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
