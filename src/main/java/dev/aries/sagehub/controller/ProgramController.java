package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.ProgramCourseRequest;
import dev.aries.sagehub.dto.request.ProgramRequest;
import dev.aries.sagehub.dto.response.ProgramCourseResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.dto.search.GetProgramsPage;
import dev.aries.sagehub.service.programservice.ProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
public class ProgramController {
	private final ProgramService programService;

	@PostMapping
	public ResponseEntity<ProgramResponse> addProgram(@RequestBody @Valid ProgramRequest request) {
		return ResponseEntity.ok(this.programService.addProgram(request));
	}

	@GetMapping
	public ResponseEntity<Page<ProgramResponse>> getPrograms(GetProgramsPage request) {
		return ResponseEntity.ok(this.programService.getPrograms(request));
	}

	@GetMapping("/{program-id}")
	public ResponseEntity<ProgramResponse> getProgram(@PathVariable("program-id")Long programId) {
		return ResponseEntity.ok(this.programService.getProgram(programId));
	}

	@PutMapping("/{program-id}")
	public ResponseEntity<ProgramResponse> updateProgram(
			@PathVariable("program-id") Long programId, @RequestBody @Valid ProgramRequest request) {
		return ResponseEntity.ok(this.programService.updateProgram(programId, request));
	}

	@PutMapping("/{program-id}/courses")
	public ResponseEntity<ProgramCourseResponse> updateProgramCourses(
			@PathVariable("program-id") Long programId, @RequestBody @Valid ProgramCourseRequest request) {
		return ResponseEntity.ok(this.programService.updateProgramCourses(programId, request));
	}

}
