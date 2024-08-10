package dev.aries.sagehub.enums;

/**
 * The {@code TokenType} class represents the different types of tokens that can be generated in the system.
 * <p>It includes the following values:</p>
 * <ul>
 * <li>{@code ACCESS_TOKEN} - Used to generate new access tokens.</li>
 * <li>{@code REFRESH_TOKEN} - Used to generate new refresh tokens.</li>
 * <li>{@code VERIFY_EMAIL} - Used for verifying a user's email address. It's typically sent to the user's
 * email address and the user must click a link
 * or enter the token into the application to verify their email address.</li>
 * <li>{@code RESET_PASSWORD} - Used for resetting a user's password. It's typically sent to the user's
 * email address and the user must enter the token and
 * their new password into the application to reset their password.</li>
 * <li>{@code VERIFY_VOUCHER} - Used for verifying a voucher.</li>
 * </ul>
 *
 * @author Jacobs Agyei
 */
public enum TokenType {
	/**
	 * It's typically used to generate a new access token after the current one expires.
	 */
	ACCESS_TOKEN,
	/**
	 * It's typically used to generate a new refresh token after the current one expires.
	 */
	REFRESH_TOKEN,
	/**
	 * This represents the token used for verifying a user's email address.
	 */
	VERIFY_EMAIL,
	/**
	 * It's typically sent to the user's email address and the user must enter the token and
	 * their new password into the application to reset their password.
	 */
	RESET_PASSWORD,
	/**
	 * This represents the token used for verifying a voucher.
	 */
	VERIFY_VOUCHER;

	@Override
	public String toString() {
		return switch (this) {
			case ACCESS_TOKEN -> "Access token";
			case REFRESH_TOKEN -> "Refresh token";
			case VERIFY_EMAIL -> "Verify email";
			case RESET_PASSWORD -> "Reset password";
			case VERIFY_VOUCHER -> "Verify voucher";
		};
	}
}
