package dev.aries.sagehub.constant;

/**
 * The ResponseMessage class contains constant string messages used for various responses
 * throughout the application. This class is final and cannot be instantiated.
 * @author Jacobs Agyei
 */
public final class ResponseMessage {

	/**
	 * Message indicating that an email has been sent successfully.
	 * The placeholder %s can be replaced with the specific email type.
	 */
	public static final String EMAIL_SENT = "%s email sent successfully. It may take a while to come.";

	/**
	 * Message indicating that the password reset was successful.
	 */
	public static final String PASSWORD_RESET_SUCCESS = "Password reset successful";

	/**
	 * Message indicating that an item has been added successfully.
	 * The placeholder %s can be replaced with the specific item type.
	 */
	public static final String ADD_SUCCESS = "%s added successfully";

	/**
	 * Private constructor to prevent instantiation of this utility class.
	 * Throws an IllegalStateException if called.
	 */
	private ResponseMessage() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
