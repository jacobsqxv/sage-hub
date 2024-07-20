package dev.aries.sagehub.constant;

public final class ValidationMessage {
	public static final String DATE_OF_BIRTH = "Date of birth must be in the past";
	public static final String NOT_NULL = " is required";
	public static final String INVALID_FORMAT = "Invalid format for ";
	public static final String INVALID_NUMBER = " must be a positive number";
	public static final String MAX_VALUE = " must be less than or equal to specified value";
	public static final String NOT_EMPTY = " must not be empty or blank";

	private ValidationMessage() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
