package dev.aries.sagehub.enums;

/**
 * The {@code Semester} class represents the semesters in an academic year.
 * This is used to distinguish between the first and second halves of an academic year,
 * typically used in educational institutions to categorize academic terms.
 * <ul>
 *     <li>{@code FIRST} - Represents the first semester of the academic year.</li>
 *     <li>{@code SECOND} - Represents the second semester of the academic year.</li>
 * </ul>
 *
 * @author Jacobs Agyei
 */
public enum Semester {
	/**
	 * Represents the first semester of the academic year.
	 */
	FIRST,
	/**
	 * Represents the second semester of the academic year.
	 */
	SECOND;

	@Override
	public String toString() {
		return switch (this) {
			case FIRST -> "First Semester";
			case SECOND -> "Second Semester";
		};
	}
}
