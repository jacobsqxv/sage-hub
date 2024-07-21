package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.ApplicantResultRequest;
import dev.aries.sagehub.dto.response.ApplicantResultsResponse;
import dev.aries.sagehub.service.applicantresultservice.ApplicantResultService;
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
public class ApplicantResultsController {
	private final ApplicantResultService applicantResultService;

	@PutMapping("{id}")
	public ResponseEntity<ApplicantResultsResponse> updateResults(
			@PathVariable Long id, @RequestBody @Valid ApplicantResultRequest request) {
		return ResponseEntity.ok(this.applicantResultService.updateApplicantResults(id, request));
	}
}
