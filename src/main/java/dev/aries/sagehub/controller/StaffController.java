package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.AddUserRequest;
import dev.aries.sagehub.dto.response.UserResponse;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.service.userservice.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
public class StaffController {

	private final UserService userService;

	@PostMapping
	@PreAuthorize("hasAnyAuthority('SCOPE_SUPER_ADMIN', 'SCOPE_ADMIN')")
	public ResponseEntity<UserResponse> addStaff(@RequestBody @Valid AddUserRequest request) {
		return ResponseEntity.ok(this.userService.addFacultyMember(request, RoleEnum.STAFF.name()));
	}
}
