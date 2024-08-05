package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.AddUserRequest;
import dev.aries.sagehub.dto.response.UserResponse;
import dev.aries.sagehub.enums.RoleEnum;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users/staff")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SCOPE_SUPER_ADMIN', 'SCOPE_ADMIN', 'SCOPE_STAFF')")
@Tag(name = "User", description = "User management")
public class StaffController {

	private final UserService userService;

	@PostMapping
	@PreAuthorize("hasAnyAuthority('SCOPE_SUPER_ADMIN', 'SCOPE_ADMIN')")
	@Operation(summary = "Add a staff",
			description = "Add a staff to the system",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "201", description = "Staff added successfully",
			content = {@Content(schema = @Schema(implementation = UserResponse.class))})
	public ResponseEntity<UserResponse> addStaff(@RequestBody @Valid AddUserRequest request) {
		return new ResponseEntity<>(userService.addFacultyMember(request, RoleEnum.STAFF.name()),
				HttpStatus.CREATED);
	}
}
