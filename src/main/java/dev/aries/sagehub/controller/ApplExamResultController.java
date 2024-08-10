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
@RequestMapping("api/v1/applications/{id}/results")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_APPLICANT')")
@Tag(name = "Application")
public class ApplExamResultController {
	private final ExamResultService examResultService;

	@PostMapping
	@Operation(summary = "Add new exam results",
			description = "Add new exam results for applicant by id",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "201", description = "Exam results added successfully",
			content = {@Content(schema = @Schema(implementation = ExamResultResponse.class))})
	public ResponseEntity<ExamResultResponse> addResults(
			@PathVariable("id") Long applicationId, @RequestBody @Valid ExamResultRequest request) {
		return new ResponseEntity<>(examResultService.addExamResults(applicationId, request),
				HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "Get all exam results",
			description = "Get all exam results of applicant by id",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Exam results retrieved successfully",
			content = {@Content(schema = @Schema(implementation = ExamResultResponse.class))})
	public ResponseEntity<List<ExamResultResponse>> getResults(@PathVariable("id") Long applicationId) {
		return ResponseEntity.ok(examResultService.getExamResults(applicationId));
	}

	@GetMapping("{result-id}")
	@Operation(summary = "Get exam results",
			description = "Get exam results by application id and result id",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Applicant exam results retrieved successfully",
			content = {@Content(schema = @Schema(implementation = ExamResultResponse.class))})
	public ResponseEntity<ExamResultResponse> getResult(@PathVariable("id") Long applicationId,
								@PathVariable("result-id") Long resultId) {
		return ResponseEntity.ok(examResultService.getExamResult(applicationId, resultId));
	}

	@PutMapping("{result-id}")
	@Operation(summary = "Update exam results",
			description = "Update exam results by application id and result id",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Exam results updated successfully",
			content = {@Content(schema = @Schema(implementation = ExamResultResponse.class))})
	public ResponseEntity<ExamResultResponse> updateResults(@PathVariable("id") Long applicationId,
			@PathVariable("result-id") Long resultId, @RequestBody @Valid ExamResultRequest request) {
		return ResponseEntity.ok(examResultService.updateExamResults(applicationId, resultId, request));
	}

	@DeleteMapping("{result-id}")
	@Operation(summary = "Delete exam results",
			description = "Delete exam results by application id and result id",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "204", description = "Exam results deleted successfully")
	public ResponseEntity<Void> deleteResults(@PathVariable("id") Long applicationId,
			@PathVariable("result-id") Long resultId) {
		examResultService.deleteExamResults(applicationId, resultId);
		return ResponseEntity.noContent().build();
	}
}
