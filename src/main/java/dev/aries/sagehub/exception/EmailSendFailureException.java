package dev.aries.sagehub.exception;

import dev.aries.sagehub.constant.ExceptionConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmailSendFailureException extends RuntimeException {
	public EmailSendFailureException() {
		super(ExceptionConstants.EMAIL_SENDING_FAILURE);
	}
}
