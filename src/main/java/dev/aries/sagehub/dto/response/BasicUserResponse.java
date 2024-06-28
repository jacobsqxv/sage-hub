package dev.aries.sagehub.dto.response;

import java.time.LocalDate;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record BasicUserResponse(
		Long id,
		String profilePicture,
		Optional<Long> studentId,
		Optional<Long> staffId,
		String fullname,
		String username,
		String primaryEmail,
		String role,
		String gender,
		String status,
		LocalDate dateOfBirth,
		String secondaryEmail
) {
}
