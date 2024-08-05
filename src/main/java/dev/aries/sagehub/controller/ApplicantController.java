package dev.aries.sagehub.controller;

import java.util.List;

import dev.aries.sagehub.dto.request.ApplicantRequest;
import dev.aries.sagehub.dto.request.ApplicantResultRequest;
import dev.aries.sagehub.dto.request.ProgramChoicesRequest;
import dev.aries.sagehub.dto.response.ApplicantResponse;
import dev.aries.sagehub.dto.response.ApplicantResultsResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.service.applicantresultservice.ApplicantResultService;
import dev.aries.sagehub.service.applicantservice.ApplicantService;
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
@RequestMapping("/api/v1/applicants")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_APPLICANT')")
@Tag(name = "Applicant", description = "Manage applicants and their information")
public class ApplicantController {
	private final ApplicantService applicantService;
	private final ApplicantResultService applicantResultService;

	@PostMapping
	@Operation(summary = "Start application",
			description = "Start application process by adding personal information",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "201", description = "Applicant personal information added successfully",
			content = {@Content(schema = @Schema(implementation = ApplicantResponse.class))})
	public ResponseEntity<ApplicantResponse> addPersonalInformation(
			@RequestBody @Valid ApplicantRequest request) {
		return new ResponseEntity<>(applicantService.addPersonalInfo(request), HttpStatus.CREATED);
	}

	@GetMapping("{id}")
	@Operation(summary = "Get applicant",
			description = "Get applicant information by ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Applicant information retrieved successfully",
			content = {@Content(schema = @Schema(implementation = ApplicantResponse.class))})
	public ResponseEntity<ApplicantResponse> getApplicant(
			@PathVariable Long id) {
		return ResponseEntity.ok(applicantService.getApplicant(id));
	}

	@PutMapping("{id}/program-choices")
	@Operation(summary = "Update program choices",
			description = "Update applicant program choices by ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Applicant program choices updated successfully",
			content = {@Content(schema = @Schema(implementation = ProgramResponse.class))})
	public ResponseEntity<List<ProgramResponse>> updateProgramChoices(
			@PathVariable Long id, @RequestBody @Valid ProgramChoicesRequest request) {
		return ResponseEntity.ok(applicantService.updateApplicantProgramChoices(id, request));
	}

	@PostMapping("{id}/results")
	@Operation(summary = "Add applicant results",
			description = "Add applicant results by ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "201", description = "Applicant results added successfully",
			content = {@Content(schema = @Schema(implementation = ApplicantResultsResponse.class))})
	public ResponseEntity<ApplicantResultsResponse> addResults(
			@PathVariable Long id, @RequestBody @Valid ApplicantResultRequest request) {
		return new ResponseEntity<>(applicantResultService.addApplicantResults(id, request),
				HttpStatus.CREATED);
	}
}
