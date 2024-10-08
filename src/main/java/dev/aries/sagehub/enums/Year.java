package dev.aries.sagehub.enums;

/**
 * The {@code Year} class represents the academic years in a typical educational program.
 * This is used to categorize students and courses based on their academic year,
 * facilitating the organization of academic information and schedules.
 * <ul>
 *     <li>{@code FIRST} - Represents the first academic year.</li>
 *     <li>{@code SECOND} - Represents the second academic year.</li>
 *     <li>{@code THIRD} - Represents the third academic year.</li>
 *     <li>{@code FOURTH} - Represents the fourth academic year.</li>
 *     <li>{@code FIFTH} - Represents the fifth academic year, applicable in programs exceeding four years.</li>
 *     <li>{@code SIXTH} - Represents the sixth academic year, for programs that extend beyond five years.</li>
 * </ul>
 *
 * @author Jacobs Agyei
 */
public enum Year {
	/**
	 * The first academic year.
	 */
	FIRST,
	/**
	 * The second academic year.
	 */
	SECOND,
	/**
	 * The third academic year.
	 */
	THIRD,
	/**
	 * The fourth academic year.
	 */
	FOURTH,
	/**
	 * The fifth academic year, applicable in programs exceeding four years.
	 */
	FIFTH,
	/**
	 * The sixth academic year, for programs that extend beyond five years.
	 */
	SIXTH;

	@Override
	public String toString() {
		return switch (this) {
			case FIRST -> "First";
			case SECOND -> "Second";
			case THIRD -> "Third";
			case FOURTH -> "Fourth";
			case FIFTH -> "Fifth";
			case SIXTH -> "Sixth";
		};
	}
}
