package dev.aries.sagehub.constant;

/**
 * This class contains constants that are used as patterns in input validation throughout the application.
 * It is a utility class and cannot be instantiated.
 * @author Jacobs Agyei
 */
public final class Patterns {

	/**
	 * Regular expression for validating email addresses. It supports standard email
	 * formats, including alphanumeric characters, periods, and hyphens before the @
	 * symbol, followed by domain parts separated by dots. The domain part allows 2 to 4
	 * alphanumeric characters.
	 */
	public static final String EMAIL = "^[A-Za-z0-9._%+-]{3,64}@[A-Za-z0-9-]+(\\.[A-Za-z]{2,4}){1,2}$";

	/**
	 * Regular expression for validating IDs.
	 * It allows for IDs that are 7 to 10 digits long, catering to a range of identification numbers.
	 */
	public static final String ID = "^\\d{7,10}$";

	/**
	 * Regular expression for validating names.
	 * It allows for names with alphabetic characters, possibly including
	 * apostrophes, commas, periods, spaces, and hyphens.
	 * This pattern supports first and last names, including compound names.
	 */
	public static final String NAME = "^([A-Za-z]{3,20}(?:-?[A-Za-z]{3,20})?\\s?){1,5}$";

	/**
	 * Regular expression for validating location names.
	 * It allows for location names with alphabetic characters, including spaces, commas, periods, and hyphens.
	 */
	public static final String LOCATION = "^([A-Za-z]{3,20}(?:-?[A-Za-z]{3,20})?\\s?){1,3}$";

	/**
	 * Regular expression for validating street addresses. It allows for street addresses
	 * with alphanumeric characters, including spaces, commas, periods, and hyphens.
	 */
	public static final String STREET = "^(?:(\\d){3,7},?\\s)?([A-Za-z]{3,20}(?:-?[A-Za-z]{3,20})?\\s?){1,4}$";

	/**
	 * Regular expression for validating passwords.
	 * It requires at least one digit, one lowercase letter, one uppercase letter, one special character,
	 * and at least 8 characters in total without any spaces.
	 */
	public static final String PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,30}$";

	/**
	 * Regular expression for validating postal addresses.
	 * It supports alphanumeric characters, including spaces, commas, periods, and hyphens.
	 */
	public static final String POSTAL_ADDRESS = "^(Box\\s[A-Z0-9]{4,20},?\\s)?" +
			"([A-Za-z]{4,20}(?:-?[A-Za-z]{4,20})?\\s?){1,3}$";

	/**
	 * Regular expression for validating JWT tokens.
	 */
	public static final String JWT_PATTERN = "^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]*$";

	/**
	 * Regular expression for validating grades.
	 */
	public static final String GRADE = "^[A-F][+-]?$";

	private Patterns() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}

}
