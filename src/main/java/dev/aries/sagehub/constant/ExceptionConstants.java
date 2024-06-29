package dev.aries.sagehub.constant;

public final class ExceptionConstants {

	public static final String UTILITY_CLASS = "This is a utility class";

	public static final String UNEXPECTED_VALUE = "Unexpected value";

	public static final String ACCOUNT_LOCKED = "User account is locked";

	public static final String ACCOUNT_DISABLED = "User account has been disabled";

	public static final String INVALID_CREDENTIALS = "Invalid username or password";

	public static final String INVALID_CURRENT_PASSWORD = "Current password is incorrect";

	public static final String UNAUTHORIZED_ACCESS = "Not authorized to access this resource";

	public static final String NO_INFO_FOUND = "No %s information found for the user";

	public static final String NOT_FOUND = "%s not found or does not exist";

	public static final String NO_UPDATE_STRATEGY = "No update strategy found for type: %s";

	public static final String PROGRAM_FETCH_ERROR = "One or more programs not found";

	public static final String NAME_EXISTS = "%s with name %s already exists";

	public static final String CANNOT_UPDATE_NAME = "%s name cannot be updated";

	private ExceptionConstants() {
		throw new IllegalStateException(UTILITY_CLASS);
	}

}
