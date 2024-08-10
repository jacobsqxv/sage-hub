package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.CrseOffrgRequest;
import dev.aries.sagehub.dto.request.ProgramRequest;
import dev.aries.sagehub.dto.response.CrseOffrgResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.dto.search.GetCrseOffrgPage;
import dev.aries.sagehub.dto.search.GetProgramsPage;
import dev.aries.sagehub.service.programservice.CourseOfferingService;
import dev.aries.sagehub.service.programservice.ProgramService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/programs")
@RequiredArgsConstructor
@Tag(name = "Program", description = "Endpoints for managing programs")
@PreAuthorize("hasAnyAuthority('SCOPE_SUPER_ADMIN', 'SCOPE_ADMIN')")
public class ProgramController {
	private final ProgramService programService;
	private final CourseOfferingService courseOfferingService;

	@PostMapping
	@Operation(summary = "Add program", description = "Add a new program",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "201", description = "Program added successfully",
			content = {@Content(schema = @Schema(implementation = ProgramResponse.class))})
	public ResponseEntity<ProgramResponse> addProgram(@RequestBody @Valid ProgramRequest request) {
		return new ResponseEntity<>(programService.addProgram(request), HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "Get programs", description = "Get programs as page, supports search and filter",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Programs retrieved successfully",
			content = {@Content(schema = @Schema(implementation = ProgramResponse.class))})
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Page<ProgramResponse>> getPrograms(
			GetProgramsPage request, @PageableDefault Pageable pageable) {
		return ResponseEntity.ok(programService.getPrograms(request, pageable));
	}

	@GetMapping("/{program-id}")
	@Operation(summary = "Get program", description = "Get program by ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Program retrieved successfully",
			content = {@Content(schema = @Schema(implementation = ProgramResponse.class))})
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ProgramResponse> getProgram(@PathVariable("program-id") Long programId) {
		return ResponseEntity.ok(programService.getProgram(programId));
	}

	@PutMapping("/{program-id}")
	@Operation(summary = "Update program", description = "Update program by ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Program updated successfully",
			content = {@Content(schema = @Schema(implementation = ProgramResponse.class))})
	public ResponseEntity<ProgramResponse> updateProgram(
			@PathVariable("program-id") Long programId, @RequestBody @Valid ProgramRequest request) {
		return ResponseEntity.ok(programService.updateProgram(programId, request));
	}

	@GetMapping("/{program-id}/courses")
	@Operation(summary = "Get program courses", description = "Get program courses by program ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Program courses retrieved successfully",
			content = {@Content(schema = @Schema(implementation = CrseOffrgResponse.class))})
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Page<CrseOffrgResponse>> getProgramCourses(@PathVariable("program-id") Long programId,
							GetCrseOffrgPage request, @PageableDefault Pageable pageable) {
		return ResponseEntity.ok(courseOfferingService.getCrseOffgForProgram(programId, request, pageable));
	}

	@PostMapping("/{program-id}/courses")
	@Operation(summary = "Add course config", description = "Add new course config for a given period",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "201", description = "Program course added successfully",
			content = {@Content(schema = @Schema(implementation = CrseOffrgResponse.class))})
	public ResponseEntity<CrseOffrgResponse> addProgramCourse(
			@PathVariable("program-id") Long programId, @RequestBody @Valid CrseOffrgRequest request) {
		return new ResponseEntity<>(courseOfferingService.addCrseOffgForProgram(programId, request),
				HttpStatus.CREATED);
	}

	@DeleteMapping("/{program-id}/courses/{id}")
	@Operation(summary = "Delete course config", description = "Delete course config by ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "204", description = "Program course deleted successfully")
	public ResponseEntity<Void> deleteProgramCourse(
			@PathVariable("program-id") Long programId, @PathVariable("id") Long id) {
		courseOfferingService.deleteCourseConfig(programId, id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
