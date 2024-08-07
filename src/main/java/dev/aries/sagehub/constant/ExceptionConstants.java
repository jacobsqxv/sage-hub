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
	public static final String INVALID_CREDENTIALS = "Username or password is incorrect";
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
	 * Message for used and or expired tokens. The placeholder is for the type of token.
	 */
	public static final String INVALID_TOKEN = "%s has expired or is already used";
	/**
	 * Message for email sending failure.
	 */
	public static final String EMAIL_SENDING_FAILURE = "There was an error sending the email";
	/**
	 * Message for voucher limit reached.
	 */
	public static final String VOUCHERS_LIMIT = "Vouchers limit reached for the selected academic year";
	/**
	 * Message for authentication failure.
	 */
	public static final String AUTHENTICATION_FAILED = "Authentication failed";
	/**
	 * Message for key generation failure.
	 */
	public static final String KEY_GENERATION_FAILED = "Failed to generate key pair";
	/**
	 * Message for entity already exists. The placeholder is for the type of entity.
	 */
	public static final String ALREADY_EXISTS = "%s already exists";
	/**
	 * Message for rate limit exceeded.
	 */
	public static final String RATE_LIMIT_EXCEEDED = "Too many requests. Try again in 30 minutes";
	/**
	 * Message for password already used.
	 */
	public static final String PASSWORD_ALREADY_USED = "Password has already been used";

	/**
	 * Message for invalid request.
	 */
	public static final String INVALID_REQUEST = "There was a problem with the request format";

	/**
	 * Message for invalid enum value.
	 */
	public static final String ENUM_VALUE_INVALID = "The provided value: '%s', is invalid";

	/**
	 * Message for existing results.
	 */
	public static final String EXISTING_RESULTS = "Results already exist for the provided index number";

	/**
	 * Message for blacklisted token.
	 */
	public static final String BLACKLISTED_TOKEN = "An access token has already been issued for this refresh token";
	/**
	 * Private constructor to prevent instantiation of this utility class.
	 * Throws an exception if instantiation is attempted.
	 */
	private ExceptionConstants() {
		throw new IllegalStateException(UTILITY_CLASS);
	}

}
