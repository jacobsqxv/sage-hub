package dev.aries.sagehub.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RateLimitException extends RuntimeException {
	public RateLimitException(String message) {
		super(message);
	}
}
