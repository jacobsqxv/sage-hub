package dev.aries.sagehub.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnauthorizedAccessException extends RuntimeException {

	private final String msg;

}
