package dev.aries.sagehub.constant;

public final class ValidationMessage {
	public static final String EMAIL = "Invalid email format";
	public static final String PHONE_NUMBER = "Invalid phone number format";
	public static final String STREET = "Invalid street format";
	public static final String CITY = "Invalid city format";
	public static final String REGION = "Invalid region format";
	public static final String POSTAL_ADDRESS = "Invalid postal address format";
	public static final String NAME = "Invalid name format";
	public static final String PASSWORD = "Invalid password format";
	public static final String DATE_OF_BIRTH = "Date of birth must be in the past";
	public static final String NOT_NULL = "One or more fields are required";
	public static final String INVALID_NUMBER = "Number must be positive";
	public static final String MAX_VALUE = "Number must be less than or equal to specified value";

	private ValidationMessage() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
