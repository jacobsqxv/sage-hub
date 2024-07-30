package dev.aries.sagehub.constant;

/**
 * The ValidationMessage class contains constant string messages used for various
 * validation responses throughout the application. This class is final and cannot be
 * instantiated.
 * @author Jacobs Agyei
 */
public final class ValidationMessage {

	/**
	 * Message indicating that the date of birth must be in the past.
	 */
	public static final String DATE_OF_BIRTH = "Date of birth must be in the past";

	/**
	 * Message indicating that a field is required and must not be null.
	 */
	public static final String NOT_NULL = " is required";

	/**
	 * Message indicating that a field has an invalid format.
	 */
	public static final String INVALID_FORMAT = "Invalid format for ";

	/**
	 * Message indicating that a number must be positive.
	 */
	public static final String INVALID_NUMBER = " must be a positive number";

	/**
	 * Message indicating that a value must be less than or equal to a specified value.
	 */
	public static final String MAX_VALUE = " must be less than or equal to specified value";

	/**
	 * Message indicating that a field must not be empty or blank.
	 */
	public static final String NOT_EMPTY = " must not be empty or blank";

	/**
	 * Private constructor to prevent instantiation of this utility class. Throws an
	 * IllegalStateException if called.
	 */
	private ValidationMessage() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
