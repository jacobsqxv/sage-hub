package dev.aries.sagehub.enums;

/**
 * Enum representing the different types of tokens that can be generated in the system.
 *
 * <p>
 * It includes the following values:
 * <ul>
 * <li>VERIFY_EMAIL: This token type is used for verifying a user's email address.</li>
 * <li>RESET_PASSWORD: This token type is used for resetting a user's password.</li>
 * </ul>
 * </p>
 *
 * @author Jacobs Agyei
 */
public enum TokenType {
	/**
	 * It's typically sent to the user's email address and the user must click a link
	 * or enter the token into the application to verify their email address.
	 */
	VERIFY_EMAIL,
	/**
	 * It's typically sent to the user's email address and the user must enter the token and
	 * their new password into the application to reset their password.
	 */
	RESET_PASSWORD
}
