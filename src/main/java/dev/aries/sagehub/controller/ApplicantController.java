package dev.aries.sagehub.controller;

import java.util.List;

import dev.aries.sagehub.dto.request.ApplicantResultRequest;
import dev.aries.sagehub.dto.request.ApplicantRequest;
import dev.aries.sagehub.dto.request.ProgramChoicesRequest;
import dev.aries.sagehub.dto.response.ApplicantResultsResponse;
import dev.aries.sagehub.dto.response.ApplicantResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.service.applicantresultservice.ApplicantResultService;
import dev.aries.sagehub.service.applicantservice.ApplicantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
public class ApplicantController {
	private final ApplicantService applicantService;
	private final ApplicantResultService applicantResultService;

	@PostMapping
	public ResponseEntity<ApplicantResponse> addPersonalInformation(
			@RequestBody @Valid ApplicantRequest request) {
		return ResponseEntity.ok(this.applicantService.addPersonalInfo(request));
	}

	@GetMapping("{id}")
	public ResponseEntity<ApplicantResponse> getApplicant(
			@PathVariable Long id) {
		return ResponseEntity.ok(this.applicantService.getApplicant(id));
	}

	@PutMapping("{id}/program-choices")
	public ResponseEntity<List<ProgramResponse>> updateProgramChoices(
			@PathVariable Long id, @RequestBody @Valid ProgramChoicesRequest request) {
		return ResponseEntity.ok(this.applicantService.updateApplicantProgramChoices(id, request));
	}

	@PostMapping("{id}/results")
	public ResponseEntity<ApplicantResultsResponse> addResults(
			@PathVariable Long id, @RequestBody @Valid ApplicantResultRequest request) {
		return ResponseEntity.ok(this.applicantResultService.addApplicantResults(id, request));
	}
}
