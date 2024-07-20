package dev.aries.sagehub.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class KeyUtilException extends RuntimeException {
	public KeyUtilException(String message) {
		super(message);
	}
}
