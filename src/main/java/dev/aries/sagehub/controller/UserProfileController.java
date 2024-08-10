package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.UserProfileRequest;
import dev.aries.sagehub.dto.response.UserProfileResponse;
import dev.aries.sagehub.service.userprofileservice.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users/{user-id}/profile")
@Tag(name = "User")
public class UserProfileController {
	private final UserProfileService userProfileService;

	@GetMapping
	@Operation(summary = "Get user info",
			description = "Get a user's personal and emergency contact information",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "User info retrieved successfully",
			content = {@Content(schema = @Schema(implementation = UserProfileResponse.class))})
	public ResponseEntity<UserProfileResponse> getUserInfo(@PathVariable("user-id") Long id) {
		return ResponseEntity.ok(userProfileService.getUserProfile(id));
	}

	@PutMapping
	@Operation(summary = "Update user info",
			description = "Update user's personal and emergency contact information",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "User info updated successfully",
			content = {@Content(schema = @Schema(implementation = UserProfileResponse.class))})
	public ResponseEntity<UserProfileResponse> updateUserInfo(
			@PathVariable("user-id") Long id, @RequestBody @Valid UserProfileRequest request) {
		return ResponseEntity.ok(userProfileService.updateUserProfile(id, request));
	}
}
