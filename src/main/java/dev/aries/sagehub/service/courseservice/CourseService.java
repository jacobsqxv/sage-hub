package dev.aries.sagehub.service.courseservice;

import dev.aries.sagehub.dto.request.CourseRequest;
import dev.aries.sagehub.dto.response.CourseResponse;
import dev.aries.sagehub.dto.search.GetCoursesPage;

import org.springframework.data.domain.Page;

public interface CourseService {
	CourseResponse addCourse(CourseRequest request);

	CourseResponse getCourse(Long courseId);

	Page<CourseResponse> getCourses(GetCoursesPage request);

	CourseResponse updateCourse(Long id, CourseRequest request);

}
