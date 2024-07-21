package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.ContactInfoRequest;
import dev.aries.sagehub.dto.response.ContactInfoResponse;
import dev.aries.sagehub.service.contactinfoservice.ContactInfoInterface;
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
@RequestMapping("api/v1/users/contact-info")
public class ContactInfoController {
	private final ContactInfoInterface contactInfoService;

	@GetMapping("{contact-info-id}")
	public ResponseEntity<ContactInfoResponse> getContactInfo(@PathVariable("contact-info-id") Long id) {
		return ResponseEntity.ok(this.contactInfoService.getContactInfo(id));
	}

	@PutMapping("{contact-info-id}")
	public ResponseEntity<ContactInfoResponse> updateContactInfo(
			@PathVariable("contact-info-id") Long id,
			@RequestBody @Valid ContactInfoRequest request) {
		return ResponseEntity.ok(this.contactInfoService.updateContactInfo(id, request));
	}

}
