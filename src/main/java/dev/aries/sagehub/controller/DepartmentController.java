package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.DepartmentRequest;
import dev.aries.sagehub.dto.response.DepartmentResponse;
import dev.aries.sagehub.service.departmentservice.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<DepartmentResponse> addDepartment(@RequestBody @Valid DepartmentRequest request) {
		return ResponseEntity.ok(departmentService.addDepartment(request));
	}

	@GetMapping("/{department-id}")
	public ResponseEntity<DepartmentResponse> getDepartment(@PathVariable("department-id") Long departmentId) {
		return ResponseEntity.ok(departmentService.getDepartment(departmentId));
	}

	@GetMapping
	public ResponseEntity<DepartmentResponse> getAllDepartments() {
		return ResponseEntity.ok(departmentService.getAllDepartments());
	}

	@PutMapping("/{department-id}")
	public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable("department-id") Long departmentId,
			@RequestBody @Valid DepartmentRequest request) {
		return ResponseEntity.ok(departmentService.updateDepartment(departmentId, request));
	}

	@PutMapping("/{department-id}/archive")
	public ResponseEntity<DepartmentResponse> archiveDepartment(@PathVariable("department-id") Long departmentId) {
		return ResponseEntity.ok(departmentService.archiveDepartment(departmentId));
	}
}
