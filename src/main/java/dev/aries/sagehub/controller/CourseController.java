package dev.aries.sagehub.controller;

import java.util.List;

import dev.aries.sagehub.dto.request.CourseRequest;
import dev.aries.sagehub.dto.response.CourseResponse;
import dev.aries.sagehub.service.courseservice.CourseService;
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
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SCOPE_SUPER_ADMIN', 'SCOPE_ADMIN')")
public class CourseController {
	private final CourseService courseService;

	@PostMapping
	public ResponseEntity<CourseResponse> addCourse(@RequestBody @Valid CourseRequest request) {
		return ResponseEntity.ok(courseService.addCourse(request));
	}

	@GetMapping("/{course-id}")
	public ResponseEntity<CourseResponse> getCourse(@PathVariable("course-id") Long courseId) {
		return ResponseEntity.ok(courseService.getCourse(courseId));
	}

	@GetMapping
	public ResponseEntity<List<CourseResponse>> getCourses() {
		return ResponseEntity.ok(courseService.getCourses());
	}

	@PutMapping("/{course-id}")
	public ResponseEntity<CourseResponse> updateCourse(
			@PathVariable("course-id") Long courseId, @RequestBody @Valid CourseRequest request) {
		return ResponseEntity.ok(courseService.updateCourse(courseId, request));
	}

}
