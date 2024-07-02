package dev.aries.sagehub.exception;

import dev.aries.sagehub.constant.ExceptionConstants;

public class EmailSendFailureException extends RuntimeException {
	public EmailSendFailureException() {
		super(ExceptionConstants.EMAIL_SENDING_FAILURE);
	}
}
