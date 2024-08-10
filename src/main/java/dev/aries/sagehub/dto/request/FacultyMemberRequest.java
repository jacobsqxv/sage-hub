package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record AddUserRequest(
		RoleEnum role,
		@NotNull(message = "Basic info" + NOT_NULL)
		@Valid
		@Schema(implementation = UserInfoRequest.class)
		UserInfoRequest userInfo,
		@NotNull(message = "Emergency contact"  + NOT_NULL)
		@Valid
		@Schema(implementation = EmergencyInfoRequest.class)
		EmergencyInfoRequest emergencyInfo
) {
}
