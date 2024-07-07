package dev.aries.sagehub.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing the common titles.
 * <ul>
 * <li>{@code MR} - Represents the title Mister.</li>
 * <li>{@code MISS} - Represents the title Miss.</li>
 * <li>{@code MRS} - Represents the title Missus.</li>
 * </ul>
 * @author Jacobs Agyei
 */
@Getter
@RequiredArgsConstructor
public enum Title {
	/**
	 * This represents the title Mister.
	 */
	MR("Mr."),
	/**
	 * This represents the title Miss.
	 */
	MISS("Miss."),
	/**
	 * This represents the title Missus.
	 */
	MRS("Mrs.");

	private final String value;
}
