package dev.aries.sagehub.service.courseservice;

import java.util.List;

import dev.aries.sagehub.dto.request.CourseRequest;
import dev.aries.sagehub.dto.response.CourseResponse;

public interface CourseService {
	CourseResponse addCourse(CourseRequest request);

	CourseResponse getCourse(Long courseId);

	List<CourseResponse> getAllCourses();

	CourseResponse updateCourse(Long id, CourseRequest request);

	CourseResponse archiveCourse(Long courseId);
}
