package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.ContactInfoRequest;
import dev.aries.sagehub.dto.response.ContactInfoResponse;
import dev.aries.sagehub.service.contactinfoservice.ContactInfoInterface;
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
@RequestMapping("api/v1/users/{user-id}/contact-info")
@Tag(name = "User")
public class ContactInfoController {
	private final ContactInfoInterface contactInfoService;

	@GetMapping
	@Operation(summary = "Get contact info",
			description = "Get contact info of a user",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Contact info retrieved successfully",
			content = {@Content(schema = @Schema(implementation = ContactInfoResponse.class))})
	public ResponseEntity<ContactInfoResponse> getContactInfo(@PathVariable("user-id") Long id) {
		return ResponseEntity.ok(contactInfoService.getContactInfo(id));
	}

	@PutMapping
	@Operation(summary = "Update contact info",
			description = "Update contact info of a user",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Contact info updated successfully",
			content = {@Content(schema = @Schema(implementation = ContactInfoResponse.class))})
	public ResponseEntity<ContactInfoResponse> updateContactInfo(
			@PathVariable("user-id") Long id,
			@RequestBody @Valid ContactInfoRequest request) {
		return ResponseEntity.ok(contactInfoService.updateContactInfo(id, request));
	}

}
