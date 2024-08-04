package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.ApplicantResultRequest;
import dev.aries.sagehub.dto.response.ApplicantResultsResponse;
import dev.aries.sagehub.service.applicantresultservice.ApplicantResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/applicants/results")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_APPLICANT')")
@Tag(name = "Applicant")
public class ApplicantResultsController {
	private final ApplicantResultService applicantResultService;

	@PutMapping("{id}")
	@Operation(summary = "Update applicant results",
			description = "Update applicant results by ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Applicant results updated successfully",
			content = {@Content(schema = @Schema(implementation = ApplicantResultsResponse.class))})
	public ResponseEntity<ApplicantResultsResponse> updateResults(
			@PathVariable Long id, @RequestBody @Valid ApplicantResultRequest request) {
		return ResponseEntity.ok(applicantResultService.updateApplicantResults(id, request));
	}
}
