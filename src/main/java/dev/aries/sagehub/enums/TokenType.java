package dev.aries.sagehub.enums;

/**
 * Enum representing the different types of tokens that can be generated in the system.
 *
 * <p>
 * It includes the following values:
 * <ul>
 * <li>ACCESS_TOKEN: This token type is used to generate new access tokens.</li>
 * <li>REFRESH_TOKEN: This token type is used to generate new refresh tokens.</li>
 * <li>VERIFY_EMAIL: This token type is used for verifying a user's email address.</li>
 * <li>RESET_PASSWORD: This token type is used for resetting a user's password.</li>
 * <li>VERIFY_VOUCHER: This token type is used for verifying a voucher.</li>
 * </ul>
 * </p>
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
	 * It's typically sent to the user's email address and the user must click a link
	 * or enter the token into the application to verify their email address.
	 */
	VERIFY_EMAIL,
	/**
	 * It's typically sent to the user's email address and the user must enter the token and
	 * their new password into the application to reset their password.
	 */
	RESET_PASSWORD,
	/**
	 * It's typically generated by the system after verifying the voucher
	 * for starting an application.
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
