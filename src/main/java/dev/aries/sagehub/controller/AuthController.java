package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.AuthRequest;
import dev.aries.sagehub.dto.request.ResetPassword;
import dev.aries.sagehub.dto.request.ResetPasswordRequest;
import dev.aries.sagehub.dto.request.VoucherRequest;
import dev.aries.sagehub.dto.response.AuthResponse;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.service.authservice.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
		return ResponseEntity.ok(this.authService.authenticateUser(request));
	}

	@PostMapping("/voucher-login")
	@PreAuthorize("isAnonymous()")
	public ResponseEntity<AuthResponse> loginWithVoucher(@RequestBody @Valid VoucherRequest request) {
		return ResponseEntity.ok(this.authService.loginWithVoucher(request));
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<AuthResponse> refreshToken(@RequestParam String token) {
		return ResponseEntity.ok(this.authService.renewRefreshToken(token));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<GenericResponse> resetPasswordRequest(@RequestBody @Valid ResetPasswordRequest request) {
		return ResponseEntity.ok(this.authService.resetPasswordRequest(request));
	}

	@PutMapping("/reset-password")
	public ResponseEntity<GenericResponse> resetPassword(@RequestParam String token,
						@RequestBody @Valid ResetPassword request) {
		return ResponseEntity.ok(this.authService.resetPassword(token, request));
	}
}
