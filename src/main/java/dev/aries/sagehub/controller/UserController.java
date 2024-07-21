package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.PasswordChangeRequest;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.service.userservice.UserService;
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
public class UserController {
	private final UserService userService;

	@PostMapping("{user-id}/change-password")
	public ResponseEntity<GenericResponse> changePassword(
			@PathVariable("user-id") Long id,
			@RequestBody @Valid PasswordChangeRequest request) {
		return ResponseEntity.ok(this.userService.changePassword(id, request));
	}
}
