package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.AdminRequest;
import dev.aries.sagehub.dto.response.AdminResponse;
import dev.aries.sagehub.service.adminservice.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users/admins")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SCOPE_SUPER_ADMIN', 'SCOPE_ADMIN')")
public class AdminController {

	private final AdminService adminService;

	@PostMapping
	@PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN')")
	public ResponseEntity<AdminResponse> addAdmin(@RequestBody @Valid AdminRequest request) {
		return ResponseEntity.ok(this.adminService.addAdmin(request));
	}
}
