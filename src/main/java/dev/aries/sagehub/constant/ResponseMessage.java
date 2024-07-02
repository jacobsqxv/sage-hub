package dev.aries.sagehub.constant;

public final class ResponseMessage {

	public static final String EMAIL_SENT = "%s email sent successfully. It may take a while to come.";
	public static final String PASSWORD_RESET_SUCCESS = "Password reset successful";

	private ResponseMessage() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
