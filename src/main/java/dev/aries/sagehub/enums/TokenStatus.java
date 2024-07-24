package dev.aries.sagehub.enums;

/**
 * Enum representing the possible states of a token of any kind.
 * <ul>
 * <li>{@code ACTIVE} - Indicates that the token is active and can be used.</li>
 * <li>{@code USED} - Indicates that the token has been used and cannot be used again.</li>
 * <li>{@code EXPIRED} - Indicates that the token has expired and cannot be used.</li>
 * </ul>
 * @author Jacobs Agyei
 */
public enum TokenStatus {
	/**
	 * This represents the active state.
	 */
	ACTIVE,
	/**
	 * This represents the used state.
	 */
	USED,
	/**
	 * This represents the expired state.
	 */
	EXPIRED;

	@Override
	public String toString() {
		return switch (this) {
			case ACTIVE -> "ACTIVE";
			case USED -> "USED";
			case EXPIRED -> "EXPIRED";
		};
	}
}
