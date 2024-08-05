package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.EmergencyContactRequest;
import dev.aries.sagehub.dto.response.EmergencyContactResponse;
import dev.aries.sagehub.service.emgcontactservice.EmergencyContactInterface;
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
@RequestMapping("api/v1/users/{user-id}/emergency-contact")
@Tag(name = "User")
public class EmergencyContactController {
	private final EmergencyContactInterface emergencyContactService;

	@GetMapping
	@Operation(summary = "Get emergency contact",
			description = "Get emergency contact of a user",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Emergency contact retrieved successfully",
			content = {@Content(schema = @Schema(implementation = EmergencyContactResponse.class))})
	public ResponseEntity<EmergencyContactResponse> getEmergencyContact(
			@PathVariable("user-id") Long id) {
		return ResponseEntity.ok(emergencyContactService.getEmergencyContact(id));
	}

	@PutMapping
	@Operation(summary = "Update emergency contact",
			description = "Update emergency contact of a user",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Emergency contact updated successfully",
			content = {@Content(schema = @Schema(implementation = EmergencyContactResponse.class))})
	public ResponseEntity<EmergencyContactResponse> updateEmergencyContact(
			@PathVariable("user-id") Long id,
			@RequestBody @Valid EmergencyContactRequest request) {
		return ResponseEntity.ok(emergencyContactService.updateEmergencyContact(id, request));
	}
}
