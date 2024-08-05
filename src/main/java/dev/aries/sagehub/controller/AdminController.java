package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.AdminRequest;
import dev.aries.sagehub.dto.response.AdminResponse;
import dev.aries.sagehub.service.adminservice.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "User management")
@RequestMapping("api/v1/users/admins")
@PreAuthorize("hasAnyAuthority('SCOPE_SUPER_ADMIN', 'SCOPE_ADMIN')")
public class AdminController {

	private final AdminService adminService;

	@Operation(summary = "Add an admin",
			description = "Add an admin to the system",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "201", description = "Admin added successfully",
			content = {@Content(schema = @Schema(implementation = AdminResponse.class))})
	@PostMapping
	@PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN')")
	public ResponseEntity<AdminResponse> addAdmin(
			@RequestBody @Valid AdminRequest request) {
		return new ResponseEntity<>(adminService.addAdmin(request), HttpStatus.CREATED);
	}
}
