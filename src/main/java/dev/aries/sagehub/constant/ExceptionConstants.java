package dev.aries.sagehub.constant;

/**
 * This class contains constants that are used as messages in exceptions throughout the application.
 * It is a utility class and cannot be instantiated.
 * @author Jacobs Agyei
 */
public final class ExceptionConstants {

	/**
	 * Message for utility class instantiation exception.
	 */
	public static final String UTILITY_CLASS = "This is a utility class";

	/**
	 * Message for unexpected value exception.
	 */
	public static final String UNEXPECTED_VALUE = "Unexpected value";

	/**
	 * Message for account locked exception. The placeholder is for the unlock time.
	 */
	public static final String ACCOUNT_LOCKED = "User account is locked until %s";

	/**
	 * Message for account disabled exception.
	 */
	public static final String ACCOUNT_DISABLED = "User account has been disabled";

	/**
	 * Message for invalid credentials exception.
	 */
	public static final String INVALID_CREDENTIALS = "Invalid username or password";

	/**
	 * Message for invalid current password exception.
	 */
	public static final String INVALID_CURRENT_PASSWORD = "Current password is incorrect";

	/**
	 * Message for unauthorized access exception.
	 */
	public static final String UNAUTHORIZED_ACCESS = "Not authorized to access this resource";

	/**
	 * Message for no information found exception. The placeholder is for the type of information.
	 */
	public static final String NO_INFO_FOUND = "No %s information found for the user";

	/**
	 * Message for not found exception. The placeholder is for the type of resource.
	 */
	public static final String NOT_FOUND = "%s not found or does not exist";

	/**
	 * Message for no update strategy found exception. The placeholder is for the type of strategy.
	 */
	public static final String NO_UPDATE_STRATEGY = "No update strategy found for type: %s";

	/**
	 * Message for program fetch error exception.
	 */
	public static final String PROGRAM_FETCH_ERROR = "One or more programs not found";

	/**
	 * Message for name exists exception.
	 * The first placeholder is for the type of resource, the second is for the name.
	 */
	public static final String NAME_EXISTS = "%s with name %s already exists";

	/**
	 * Message for cannot update name exception. The placeholder is for the type of resource.
	 */
	public static final String CANNOT_UPDATE_NAME = "%s name cannot be updated";

	/**
	 * Message for expired verification token.
	 */
	public static final String EXPIRED_TOKEN = "Token has expired";

	/**
	 * Message for email sending failure.
	 */
	public static final String EMAIL_SENDING_FAILURE = "There was an error sending the email";
	/**
	 * Message for voucher limit reached.
	 */
	public static final String VOUCHERS_LIMIT = "Vouchers limit reached for the selected academic year";
	/**
	 * Message for voucher invalid.
	 */
	public static final String VOUCHER_INVALID = "Voucher has expired or is already used";
	/**
	 * Message for token invalid.
	 */
	public static final String AUTHENTICATION_FAILED = "Authentication failed";
	/**
	 * Private constructor to prevent instantiation of this utility class.
	 * Throws an exception if instantiation is attempted.
	 */
	private ExceptionConstants() {
		throw new IllegalStateException(UTILITY_CLASS);
	}

}
