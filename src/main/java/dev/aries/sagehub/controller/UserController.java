package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.AddUserRequest;
import dev.aries.sagehub.dto.request.AdminRequest;
import dev.aries.sagehub.dto.request.PasswordChangeRequest;
import dev.aries.sagehub.dto.response.AdminResponse;
import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.service.adminservice.AdminService;
import dev.aries.sagehub.service.userservice.UserService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management")
public class UserController {
	private final UserService userService;
	private final AdminService adminService;

	@PostMapping("{user-id}/change-password")
	@Operation(summary = "Change password",
			description = "Change user password",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Password changed successfully",
			content = {@Content(schema = @Schema(implementation = GenericResponse.class))})
	public ResponseEntity<GenericResponse> changePassword(
			@PathVariable("user-id") Long id,
			@RequestBody @Valid PasswordChangeRequest request) {
		return ResponseEntity.ok(userService.changePassword(id, request));
	}

	@Operation(summary = "Add a new admin",
			description = "Add a new admin to the system",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "201", description = "Admin added successfully",
			content = {@Content(schema = @Schema(implementation = AdminResponse.class))})
	@PostMapping("admins")
	@PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN')")
	public ResponseEntity<AdminResponse> addAdmin(
			@RequestBody @Valid AdminRequest request) {
		return new ResponseEntity<>(adminService.addAdmin(request), HttpStatus.CREATED);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('SCOPE_SUPER_ADMIN', 'SCOPE_ADMIN')")
	@Operation(summary = "Add a new faculty member",
			description = "Add a student or staff to the system",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "201", description = "Faculty member added successfully",
			content = {@Content(schema = @Schema(implementation = BasicUserResponse.class))})
	public ResponseEntity<BasicUserResponse> addFacultyMember(@RequestBody @Valid AddUserRequest request) {
		return new ResponseEntity<>(userService.addFacultyMember(request),
				HttpStatus.CREATED);
	}
}
