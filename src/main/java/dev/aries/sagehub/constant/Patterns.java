package dev.aries.sagehub.constant;

public final class Patterns {
	public static final String EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
	public static final String COURSE_CODE = "^[A-Z]{3,5}[0-9]{3}$";
	public static final String DEPARTMENT_CODE = "^[A-Z]{3,5}$";
	public static final String PHONE_NUMBER = "^(?:\\+?233|0)?([2|5])[0-9]{8}$";
	public static final String NAME = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
	public static final String ADDRESS = "^[a-zA-Z0-9]+(([',. -][a-zA-Z0-9 ])?[a-zA-Z0-9]*)*$";
	public static final String CITY = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
	public static final String REGION = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
	public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

	private Patterns() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
