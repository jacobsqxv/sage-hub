package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.EmergencyContactRequest;
import dev.aries.sagehub.dto.response.EmergencyContactResponse;
import dev.aries.sagehub.service.emgcontactservice.EmergencyContactInterface;
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
public class EmergencyContactController {
	private final EmergencyContactInterface emergencyContactService;

	@GetMapping
	public ResponseEntity<EmergencyContactResponse> getEmergencyContact(
			@PathVariable("user-id") Long id) {
		return ResponseEntity.ok(this.emergencyContactService.getEmergencyContact(id));
	}

	@PutMapping
	public ResponseEntity<EmergencyContactResponse> updateEmergencyContact(
			@PathVariable("user-id") Long id,
			@RequestBody @Valid EmergencyContactRequest request) {
		return ResponseEntity.ok(this.emergencyContactService.updateEmergencyContact(id, request));
	}
}
