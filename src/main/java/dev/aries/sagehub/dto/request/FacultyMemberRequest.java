package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

@Builder
public record FacultyMemberRequest(
		@NotNull(message = "Profile info" + NOT_NULL)
		@Valid
		@Schema(implementation = UserProfileRequest.class)
		UserProfileRequest userProfile,
		@NotNull(message = "Emergency contact"  + NOT_NULL)
		@Valid
		@Schema(implementation = EmergencyInfoRequest.class)
		EmergencyInfoRequest emergencyInfo,
		@NotNull(message = "User" + NOT_NULL)
		User user

) {
}
