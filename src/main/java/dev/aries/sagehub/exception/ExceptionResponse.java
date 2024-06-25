package dev.aries.sagehub.exception;

import java.time.LocalDateTime;
import java.util.Set;

public record ExceptionResponse(int code, LocalDateTime timestamp, Set<String> content) {
}
