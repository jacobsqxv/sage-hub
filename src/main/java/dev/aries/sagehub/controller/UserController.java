package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.ContactInfoRequest;
import dev.aries.sagehub.dto.request.EmergencyContactRequest;
import dev.aries.sagehub.dto.request.PasswordChangeRequest;
import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.dto.response.ContactInfoResponse;
import dev.aries.sagehub.dto.response.EmergencyContactResponse;
import dev.aries.sagehub.service.userservice.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("{user-id}/change-password")
	public ResponseEntity<BasicUserResponse> changePassword(
			@PathVariable("user-id") Long id,
			@RequestBody @Valid PasswordChangeRequest request) {
		return ResponseEntity.ok(this.userService.changePassword(id, request));
	}

	@GetMapping("{user-id}/contact-info")
	public ResponseEntity<ContactInfoResponse> getContactInfo(@PathVariable("user-id") Long id) {
		return ResponseEntity.ok(this.userService.getContactInfo(id));
	}

	@PutMapping("{user-id}/contact-info")
	public ResponseEntity<ContactInfoResponse> updateContactInfo(
			@PathVariable("user-id") Long id,
			@RequestBody @Valid ContactInfoRequest request) {
		return ResponseEntity.ok(this.userService.updateContactInfo(id, request));
	}

	@GetMapping("{user-id}/emergency-contact")
	public ResponseEntity<EmergencyContactResponse> getEmergencyContact(
			@PathVariable("user-id") Long id) {
		return ResponseEntity.ok(this.userService.getEmergencyContact(id));
	}

	@PutMapping("{user-id}/emergency-contact")
	public ResponseEntity<EmergencyContactResponse> updateEmergencyContact(
			@PathVariable("user-id") Long id,
			@RequestBody @Valid EmergencyContactRequest request) {
		return ResponseEntity.ok(this.userService.updateEmergencyContact(id, request));
	}
}