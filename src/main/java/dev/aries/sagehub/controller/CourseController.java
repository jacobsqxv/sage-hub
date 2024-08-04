package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.CourseRequest;
import dev.aries.sagehub.dto.response.CourseResponse;
import dev.aries.sagehub.dto.search.GetCoursesPage;
import dev.aries.sagehub.service.courseservice.CourseService;
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
@Tag(name = "Course", description = "Endpoints for managing courses")
public class CourseController {
	private final CourseService courseService;

	@PostMapping
	@Operation(summary = "Add course", description = "Add a new course",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "201", description = "Course added successfully",
			content = {@Content(schema = @Schema(implementation = CourseResponse.class))})
	public ResponseEntity<CourseResponse> addCourse(@RequestBody @Valid CourseRequest request) {
		return new ResponseEntity<>(courseService.addCourse(request), HttpStatus.CREATED);
	}

	@GetMapping("/{course-id}")
	@Operation(summary = "Get course", description = "Get course by ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Course retrieved successfully",
			content = {@Content(schema = @Schema(implementation = CourseResponse.class))})
	public ResponseEntity<CourseResponse> getCourse(@PathVariable("course-id") Long courseId) {
		return ResponseEntity.ok(courseService.getCourse(courseId));
	}

	@GetMapping
	@Operation(summary = "Get courses", description = "Get courses as page, supports search and filter",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Courses retrieved successfully",
			content = {@Content(schema = @Schema(implementation = CourseResponse.class))})
	public ResponseEntity<Page<CourseResponse>> getCourses(GetCoursesPage request,
								@PageableDefault Pageable pageable) {
		return ResponseEntity.ok(courseService.getCourses(request, pageable));
	}

	@PutMapping("/{course-id}")
	@Operation(summary = "Update course", description = "Update course by ID",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Course updated successfully",
			content = {@Content(schema = @Schema(implementation = CourseResponse.class))})
	public ResponseEntity<CourseResponse> updateCourse(
			@PathVariable("course-id") Long courseId, @RequestBody @Valid CourseRequest request) {
		return ResponseEntity.ok(courseService.updateCourse(courseId, request));
	}

}
