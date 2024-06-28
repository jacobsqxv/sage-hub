package dev.aries.sagehub.constant;

public final class ValidationMessage {
	public static final String EMAIL = "Invalid email format";
	public static final String PHONE_NUMBER = "Invalid phone number format";
	public static final String ADDRESS = "Invalid address format";
	public static final String CITY = "Invalid city format";
	public static final String REGION = "Invalid region format";
	public static final String NAME = "Invalid name format";
	public static final String CODE = "Invalid code format";
	public static final String PASSWORD = "Invalid password format";
	public static final String DATE_OF_BIRTH = "Date of birth must be in the past";
	public static final String NOT_NULL = "One or more fields are required";
	public static final String INVALID_NUMBER = "Number must be positive";

	private ValidationMessage() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
