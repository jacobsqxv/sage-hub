package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.EmergencyInfoRequest;
import dev.aries.sagehub.dto.response.EmergencyInfoResponse;
import dev.aries.sagehub.service.emergencyinfoservice.EmergencyInfoService;
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
@RequestMapping("api/v1/users/{user-id}/emergency-info")
@Tag(name = "User")
public class EmergencyInfoController {
	private final EmergencyInfoService emergencyInfoService;

	@GetMapping
	@Operation(summary = "Get emergency contact information",
			description = "Get emergency contact information of a user",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Emergency contact information retrieved successfully",
			content = {@Content(schema = @Schema(implementation = EmergencyInfoResponse.class))})
	public ResponseEntity<EmergencyInfoResponse> getEmergencyInfo(
			@PathVariable("user-id") Long id) {
		return ResponseEntity.ok(emergencyInfoService.getEmergencyInfo(id));
	}

	@PutMapping
	@Operation(summary = "Update emergency contact information",
			description = "Update emergency contact information of a user",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Emergency contact updated successfully",
			content = {@Content(schema = @Schema(implementation = EmergencyInfoResponse.class))})
	public ResponseEntity<EmergencyInfoResponse> updateEmergencyInfo(
			@PathVariable("user-id") Long id,
			@RequestBody @Valid EmergencyInfoRequest request) {
		return ResponseEntity.ok(emergencyInfoService.updateEmergencyInfo(id, request));
	}
}
