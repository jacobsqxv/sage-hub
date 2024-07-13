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
	MR,
	/**
	 * This represents the title Miss.
	 */
	MISS,
	/**
	 * This represents the title Missus.
	 */
	MRS;

	@Override
	public String toString() {
		return switch (this) {
			case MR -> "Mr.";
			case MISS -> "Miss";
			case MRS -> "Mrs.";
		};
	}
}
