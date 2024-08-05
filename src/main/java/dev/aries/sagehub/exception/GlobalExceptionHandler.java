package dev.aries.sagehub.exception;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import dev.aries.sagehub.constant.ExceptionConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final LocalDateTime TIMESTAMP = LocalDateTime.now();

	@ExceptionHandler({ LockedException.class, DisabledException.class,
			UnauthorizedAccessException.class, AccessDeniedException.class })
	public ResponseEntity<ExceptionResponse> handleUnauthorizedExceptions(Exception exp) {
		logException(exp);
		Set<String> error = new HashSet<>();
		if (exp instanceof DisabledException) {
			error.add(ExceptionConstants.ACCOUNT_DISABLED);
		}
		else {
			error.add(exp.getMessage());
		}
		int code = HttpStatus.FORBIDDEN.value();
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

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleNotFoundException(EntityNotFoundException exp) {
		logException(exp);
		Set<String> error = new HashSet<>();
		error.add(exp.getMessage());
		int code = HttpStatus.NOT_FOUND.value();
		return ResponseEntity.status(code).body(new ExceptionResponse(code, TIMESTAMP, error));
	}

	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, BadCredentialsException.class})
	public ResponseEntity<ExceptionResponse> handleBadRequests(Exception exp) {
		logException(exp);
		Set<String> error = new HashSet<>();
		error.add(exp.getMessage());
		int code = HttpStatus.BAD_REQUEST.value();
		return ResponseEntity.status(code).body(new ExceptionResponse(code, TIMESTAMP, error));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ExceptionResponse> handleInvalidRequestFormat(HttpMessageNotReadableException exp) {
		logException(exp);
		Set<String> error = new HashSet<>();
		Throwable rootCause = exp.getRootCause();
		if (rootCause instanceof IllegalArgumentException exception) {
			return handleBadRequests(exception);
		}
		else {
			error.add(ExceptionConstants.INVALID_REQUEST);
		}
		int code = HttpStatus.BAD_REQUEST.value();
		return ResponseEntity.status(code).body(new ExceptionResponse(code, TIMESTAMP, error));
	}

	@ExceptionHandler({ KeyUtilException.class, RateLimitException.class, Exception.class })
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
