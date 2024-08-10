package dev.aries.sagehub.enums;
/**
 * The {@code MaritalStatus} class represents the various marital status of the user.
 * <ul>
 *     <li>{@code SINGLE} - Represents the single marital status.</li>
 *     <li>{@code MARRIED} - Represents the married marital status.</li>
 *     <li>{@code DIVORCED} - Represents the divorced marital status.</li>
 *     <li>{@code WIDOWED} - Represents the widowed marital status.</li>
 *     <li>{@code SEPARATED} - Represents the separated marital status.</li>
 * </ul>
 *
 * @author Jacobs Agyei
 */
public enum MaritalStatus {
	/**
	 * Represents the single marital status.
	 */
	SINGLE,
	/**
	 * Represents the married marital status.
	 */
	MARRIED,
	/**
	 * Represents the divorced marital status.
	 */
	DIVORCED,
	/**
	 * Represents the widowed marital status.
	 */
	WIDOWED,
	/**
	 * Represents the separated marital status.
	 */
	SEPARATED;

	@Override
	public String toString() {
		return switch (this) {
			case SINGLE -> "Single";
			case MARRIED -> "Married";
			case DIVORCED -> "Divorced";
			case WIDOWED -> "Widowed";
			case SEPARATED -> "Separated";
		};
	}
}
