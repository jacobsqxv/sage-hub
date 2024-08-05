package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.AuthRequest;
import dev.aries.sagehub.dto.request.RefreshTokenRequest;
import dev.aries.sagehub.dto.request.ResetPassword;
import dev.aries.sagehub.dto.request.ResetPasswordRequest;
import dev.aries.sagehub.dto.request.VoucherRequest;
import dev.aries.sagehub.dto.response.AuthResponse;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.service.authservice.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User authentication")
@RequestMapping("api/v1/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	@Operation(summary = "Login user",
			description = "Authenticate user")
	@ApiResponse(responseCode = "200", description = "User authenticated",
			content = {@Content(schema = @Schema(implementation = AuthResponse.class))})
	public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
		return ResponseEntity.ok(authService.authenticateUser(request));
	}

	@PostMapping("/voucher-login")
	@PreAuthorize("isAnonymous()")
	@Operation(summary = "Login user with voucher",
			description = "Authenticate user with voucher")
	@ApiResponse(responseCode = "200", description = "User authenticated",
			content = {@Content(schema = @Schema(implementation = AuthResponse.class))})
	public ResponseEntity<AuthResponse> loginWithVoucher(@RequestBody @Valid VoucherRequest request) {
		return ResponseEntity.ok(authService.loginWithVoucher(request));
	}

	@PostMapping("/refresh-token")
	@Operation(summary = "Refresh token",
			description = "Renew access token")
	@ApiResponse(responseCode = "200", description = "Token refreshed",
			content = {@Content(schema = @Schema(implementation = AuthResponse.class))})
	public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
		return ResponseEntity.ok(authService.renewRefreshToken(request));
	}

	@PostMapping("/request-password-reset")
	@Operation(summary = "Request password reset",
			description = "Send password reset link to email")
	@ApiResponse(responseCode = "200", description = "Password reset link sent",
			content = {@Content(schema = @Schema(implementation = GenericResponse.class))})
	public ResponseEntity<GenericResponse> resetPasswordRequest(@RequestBody @Valid ResetPasswordRequest request) {
		return ResponseEntity.ok(authService.resetPasswordRequest(request));
	}

	@PutMapping("/reset-password")
	@Operation(summary = "Reset user password")
	public ResponseEntity<GenericResponse> resetPassword(@RequestParam String token,
						@RequestBody @Valid ResetPassword request) {
		return ResponseEntity.ok(authService.resetPassword(token, request));
	}
}
