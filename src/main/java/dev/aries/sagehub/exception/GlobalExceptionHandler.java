package dev.aries.sagehub.exception;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import dev.aries.sagehub.constant.ExceptionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final LocalDateTime TIMESTAMP = LocalDateTime.now();
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({ LockedException.class, DisabledException.class, BadCredentialsException.class,
			UnauthorizedAccessException.class })
	public ResponseEntity<ExceptionResponse> handleUnauthorizedExceptions(Exception exp) {
		logException(exp);
		Set<String> error = new HashSet<>();
		switch (exp.getClass().getSimpleName()) {
			case "LockedException" -> error.add(ExceptionConstants.ACCOUNT_LOCKED);
			case "DisabledException" -> error.add(ExceptionConstants.ACCOUNT_DISABLED);
			default -> error.add(exp.getMessage());
		}
		int code = HttpStatus.UNAUTHORIZED.value();
		return ResponseEntity.status(code).body(new ExceptionResponse(code, TIMESTAMP, error));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleArgumentNotValidException(MethodArgumentNotValidException exp) {
		logException(exp);
		Set<String> errors = new HashSet<>();
		exp.getBindingResult().getAllErrors().forEach((error) -> {
			String errorMessage = error.getDefaultMessage();
			errors.add(errorMessage);
		});
		int code = HttpStatus.BAD_REQUEST.value();
		return ResponseEntity.status(code).body(new ExceptionResponse(code, TIMESTAMP, errors));
	}

	@ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
	public ResponseEntity<ExceptionResponse> handleBadRequests(Exception exp) {
		logException(exp);
		Set<String> error = new HashSet<>();
		error.add(exp.getMessage());
		int code = HttpStatus.BAD_REQUEST.value();
		return ResponseEntity.status(code).body(new ExceptionResponse(code, TIMESTAMP, error));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleOtherExceptions(Exception exp) {
		logException(exp);
		Set<String> error = new HashSet<>();
		error.add(exp.getMessage());
		int code = HttpStatus.INTERNAL_SERVER_ERROR.value();
		return ResponseEntity.status(code).body(new ExceptionResponse(code, TIMESTAMP, error));
	}

	private void logException(Exception ex) {
		log.error("Error occurred: ", ex);
	}
}
