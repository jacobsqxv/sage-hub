package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.PasswordChangeRequest;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.service.userservice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management")
public class UserController {
	private final UserService userService;

	@PostMapping("{user-id}/change-password")
	@Operation(summary = "Change password",
			description = "Change user password",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Password changed successfully",
			content = {@Content(schema = @Schema(implementation = GenericResponse.class))})
	public ResponseEntity<GenericResponse> changePassword(
			@PathVariable("user-id") Long id,
			@RequestBody @Valid PasswordChangeRequest request) {
		return ResponseEntity.ok(userService.changePassword(id, request));
	}
}
