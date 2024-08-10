package dev.aries.sagehub.controller;

import java.util.List;

import dev.aries.sagehub.dto.request.ApplicantInfoRequest;
import dev.aries.sagehub.dto.request.ProgramChoicesRequest;
import dev.aries.sagehub.dto.response.ApplicantInfoResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.service.applicationservice.ApplicationService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_APPLICANT')")
@Tag(name = "Application", description = "Manage applicants and their information")
public class ApplicationController {
	private final ApplicationService applicationService;

	@PostMapping("/applicant-info")
	@Operation(summary = "Start application",
			description = "Start application process by adding applicant personal information",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "201", description = "Applicant personal information added successfully",
			content = {@Content(schema = @Schema(implementation = ApplicantInfoResponse.class))})
	public ResponseEntity<ApplicantInfoResponse> addPersonalInformation(
			@RequestBody @Valid ApplicantInfoRequest request) {
		return new ResponseEntity<>(applicationService.addApplicantInfo(request), HttpStatus.CREATED);
	}

	@PutMapping("{id}/applicant-info")
	@Operation(summary = "Update applicant information",
			description = "Update applicant information by ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Applicant information updated successfully",
			content = {@Content(schema = @Schema(implementation = ApplicantInfoResponse.class))})
	public ResponseEntity<ApplicantInfoResponse> updateApplicantInfo(
			@PathVariable Long id, @RequestBody @Valid ApplicantInfoRequest request) {
		return ResponseEntity.ok(applicationService.updateApplicantInfo(id, request));
	}

	@GetMapping("{id}/applicant-info")
	@Operation(summary = "Get applicant information",
			description = "Get applicant information by ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Applicant information retrieved successfully",
			content = {@Content(schema = @Schema(implementation = ApplicantInfoResponse.class))})
	public ResponseEntity<ApplicantInfoResponse> getApplicant(
			@PathVariable Long id) {
		return ResponseEntity.ok(applicationService.getApplicantInfo(id));
	}

	@PutMapping("{id}/program-choices")
	@Operation(summary = "Update program choices",
			description = "Update applicant program choices by ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Applicant program choices updated successfully",
			content = {@Content(schema = @Schema(implementation = ProgramResponse.class))})
	public ResponseEntity<List<ProgramResponse>> updateProgramChoices(
			@PathVariable Long id, @RequestBody @Valid ProgramChoicesRequest request) {
		return ResponseEntity.ok(applicationService.updateApplicantProgramChoices(id, request));
	}

}
