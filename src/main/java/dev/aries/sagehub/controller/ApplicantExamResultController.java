package dev.aries.sagehub.controller;

import java.util.List;

import dev.aries.sagehub.dto.request.ExamResultRequest;
import dev.aries.sagehub.dto.response.ExamResultResponse;
import dev.aries.sagehub.service.examresultservice.ExamResultService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/applicants/{id}/results")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_APPLICANT')")
@Tag(name = "Applicant")
public class ExamResultController {
	private final ExamResultService examResultService;

	@PostMapping
	@Operation(summary = "Add new applicant results",
			description = "Add new applicant results by Applicant ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "201", description = "Applicant results added successfully",
			content = {@Content(schema = @Schema(implementation = ExamResultResponse.class))})
	public ResponseEntity<ExamResultResponse> addResults(
			@PathVariable Long id, @RequestBody @Valid ExamResultRequest request) {
		return new ResponseEntity<>(examResultService.addExamResults(id, request),
				HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "Get all applicant results",
			description = "Get all applicant results by Applicant ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Applicant results retrieved successfully",
			content = {@Content(schema = @Schema(implementation = ExamResultResponse.class))})
	public ResponseEntity<List<ExamResultResponse>> getResults(@PathVariable("id") Long applicantId) {
		return ResponseEntity.ok(examResultService.getExamResults(applicantId));
	}

	@GetMapping("{result-id}")
	@Operation(summary = "Get applicant results",
			description = "Get applicant results by Applicant ID and Result ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Applicant results retrieved successfully",
			content = {@Content(schema = @Schema(implementation = ExamResultResponse.class))})
	public ResponseEntity<ExamResultResponse> getResult(@PathVariable("id") Long applicantId,
								@PathVariable("result-id") Long resultId) {
		return ResponseEntity.ok(examResultService.getExamResult(applicantId, resultId));
	}

	@PutMapping("{result-id}")
	@Operation(summary = "Update applicant results",
			description = "Update applicant results by Applicant ID and Result ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Applicant results updated successfully",
			content = {@Content(schema = @Schema(implementation = ExamResultResponse.class))})
	public ResponseEntity<ExamResultResponse> updateResults(@PathVariable("id") Long applicantId,
			@PathVariable("result-id") Long resultId, @RequestBody @Valid ExamResultRequest request) {
		return ResponseEntity.ok(examResultService.updateExamResults(applicantId, resultId, request));
	}

	@DeleteMapping("{result-id}")
	@Operation(summary = "Delete applicant results",
			description = "Delete applicant results by Applicant ID and Result ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "204", description = "Applicant results deleted successfully")
	public ResponseEntity<Void> deleteResults(@PathVariable("id") Long applicantId,
			@PathVariable("result-id") Long resultId) {
		examResultService.deleteExamResults(applicantId, resultId);
		return ResponseEntity.noContent().build();
	}
}
