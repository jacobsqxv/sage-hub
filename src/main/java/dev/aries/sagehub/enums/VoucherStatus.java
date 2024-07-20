package dev.aries.sagehub.enums;

/**
 * Enum representing the possible states of a voucher.
 * <ul>
 * <li>{@code ACTIVE} - Indicates that the voucher is active and can be redeemed.</li>
 * <li>{@code USED} - Indicates that the voucher has been used and cannot be redeemed again.</li>
 * <li>{@code EXPIRED} - Indicates that the voucher has expired and cannot be redeemed.</li>
 * </ul>
 * @author Jacobs Agyei
 */
public enum VoucherStatus {
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
