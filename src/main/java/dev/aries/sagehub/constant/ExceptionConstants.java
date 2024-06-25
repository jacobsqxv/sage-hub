package dev.aries.sagehub.constant;

public final class ExceptionConstants {

	public static final String UTILITY_CLASS = "This is a utility class";

	public static final String UNEXPECTED_VALUE = "Unexpected value";

	public static final String ACCOUNT_LOCKED = "User account is locked";

	public static final String ACCOUNT_DISABLED = "User account has been disabled";

	public static final String INVALID_CREDENTIALS = "Invalid username or password";

	public static final String INVALID_CURRENT_PASSWORD = "Current password is incorrect";

	public static final String INVALID_ROLE = "Role not found or does not exist";

	public static final String UNAUTHORIZED_ACCESS = "Not authorized to access this resource";

	public static final String NO_USER_FOUND = "User not found or does not exist";

	public static final String NO_CONTACT_INFO = "No contact information found for the user";

	public static final String NO_UPDATE_STRATEGY = "No update strategy found for type: %s";

	public static final String NO_EMERGENCY_CONTACT = "No emergency contact information found for the user";

	private ExceptionConstants() {
		throw new IllegalStateException(UTILITY_CLASS);
	}

}
