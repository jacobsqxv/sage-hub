package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.DepartmentRequest;
import dev.aries.sagehub.dto.response.DepartmentResponse;
import dev.aries.sagehub.dto.search.GetDepartmentsPage;
import dev.aries.sagehub.service.departmentservice.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
@Tag(name = "Department", description = "Endpoints for managing departments")
public class DepartmentController {
	private final DepartmentService departmentService;

	@PostMapping
	@PreAuthorize("hasAnyAuthority('SCOPE_SUPER_ADMIN', 'SCOPE_ADMIN')")
	@Operation(summary = "Add department", description = "Add a new department",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "201", description = "Department added successfully",
			content = {@Content(schema = @Schema(implementation = DepartmentResponse.class))})
	public ResponseEntity<DepartmentResponse> addDepartment(@RequestBody @Valid DepartmentRequest request) {
		return new ResponseEntity<>(departmentService.addDepartment(request), HttpStatus.CREATED);
	}

	@GetMapping("/{department-id}")
	@Operation(summary = "Get department", description = "Get department by ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Department retrieved successfully",
			content = {@Content(schema = @Schema(implementation = DepartmentResponse.class))})
	public ResponseEntity<DepartmentResponse> getDepartment(@PathVariable("department-id") Long departmentId) {
		return ResponseEntity.ok(departmentService.getDepartment(departmentId));
	}

	@GetMapping
	@Operation(summary = "Get departments", description = "Get departments as page, supports search and filter",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Departments retrieved successfully",
			content = {@Content(schema = @Schema(implementation = DepartmentResponse.class))})
	public ResponseEntity<Page<DepartmentResponse>> getAllDepartments(
			GetDepartmentsPage request, @PageableDefault Pageable pageable) {
		return ResponseEntity.ok(departmentService.getDepartments(request, pageable));
	}

	@PutMapping("/{department-id}")
	@PreAuthorize("hasAnyAuthority('SCOPE_SUPER_ADMIN', 'SCOPE_ADMIN')")
	@Operation(summary = "Update department", description = "Update department by ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Department updated successfully",
			content = {@Content(schema = @Schema(implementation = DepartmentResponse.class))})
	public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable("department-id") Long departmentId,
			@RequestBody @Valid DepartmentRequest request) {
		return ResponseEntity.ok(departmentService.updateDepartment(departmentId, request));
	}

}
