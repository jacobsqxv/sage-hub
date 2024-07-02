package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.DepartmentRequest;
import dev.aries.sagehub.dto.response.DepartmentResponse;
import dev.aries.sagehub.dto.search.GetDepartmentsPage;
import dev.aries.sagehub.service.departmentservice.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {
	private final DepartmentService departmentService;

	@PostMapping
	@PreAuthorize("hasAnyAuthority('SCOPE_SUPER_ADMIN', 'SCOPE_ADMIN')")
	public ResponseEntity<DepartmentResponse> addDepartment(@RequestBody @Valid DepartmentRequest request) {
		return ResponseEntity.ok(this.departmentService.addDepartment(request));
	}

	@GetMapping("/{department-id}")
	public ResponseEntity<DepartmentResponse> getDepartment(@PathVariable("department-id") Long departmentId) {
		return ResponseEntity.ok(this.departmentService.getDepartment(departmentId));
	}

	@GetMapping
	public ResponseEntity<Page<DepartmentResponse>> getAllDepartments(
			GetDepartmentsPage request, @PageableDefault Pageable pageable) {
		return ResponseEntity.ok(this.departmentService.getDepartments(request, pageable));
	}

	@PutMapping("/{department-id}")
	@PreAuthorize("hasAnyAuthority('SCOPE_SUPER_ADMIN', 'SCOPE_ADMIN')")
	public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable("department-id") Long departmentId,
			@RequestBody @Valid DepartmentRequest request) {
		return ResponseEntity.ok(this.departmentService.updateDepartment(departmentId, request));
	}

}
