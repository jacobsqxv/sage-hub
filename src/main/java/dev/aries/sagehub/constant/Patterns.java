package dev.aries.sagehub.constant;
/**
 * This class contains constants that are used as patterns in input validation throughout the application.
 * It is a utility class and cannot be instantiated.
 * @author Jacobs Agyei
 */
public final class Patterns {
	/**
	 * Regular expression for validating email addresses.
	 * It supports standard email formats, including alphanumeric characters, periods,
	 * and hyphens before the @ symbol,
	 * followed by domain parts separated by dots. The domain part allows 2 to 4 alphanumeric characters.
	 */
	public static final String EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

	/**
	 * Regular expression for validating IDs.
	 * It allows for IDs that are 7 to 10 digits long, catering to a range of identification numbers.
	 */
	public static final String ID = "^[0-9]{7,10}$";

	/**
	 * Regular expression for validating phone numbers.
	 * It supports Ghanaian phone numbers, both landline and mobile, with or without the country code (+233 or 0).
	 * The number must start with 2 or 5 followed by 8 digits.
	 */
	public static final String PHONE_NUMBER = "^(?:\\+?233|0)?([2|5])[0-9]{8}$";

	/**
	 * Regular expression for validating names.
	 * It allows for names with alphabetic characters, possibly including
	 * apostrophes, commas, periods, spaces, and hyphens.
	 * This pattern supports first and last names, including compound names.
	 */
	public static final String NAME = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";

	/**
	 * Regular expression for validating street addresses.
	 * It supports alphanumeric characters, including spaces, commas, periods, and hyphens.
	 */
	public static final String STREET = "^[a-zA-Z0-9]+(([',. -][a-zA-Z0-9 ])?[a-zA-Z0-9]*)*$";

	/**
	 * Regular expression for validating city names.
	 * It allows for city names with alphabetic characters, including spaces, commas, periods, and hyphens.
	 */
	public static final String CITY = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";

	/**
	 * Regular expression for validating region names.
	 * Similar to city names, it allows for alphabetic characters, including spaces, commas, periods, and hyphens.
	 */
	public static final String REGION = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";

	/**
	 * Regular expression for validating passwords.
	 * It requires at least one digit, one lowercase letter, one uppercase letter, one special character,
	 * and at least 8 characters in total without any spaces.
	 */
	public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

	/**
	 * Regular expression for validating postal addresses.
	 * It supports alphanumeric characters, including spaces, commas, periods, and hyphens.
	 */
	public static final String POSTAL_ADDRESS = "^[a-zA-Z0-9]+(([',. -][a-zA-Z0-9 ])?[a-zA-Z0-9]*)*$";

	private Patterns() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
