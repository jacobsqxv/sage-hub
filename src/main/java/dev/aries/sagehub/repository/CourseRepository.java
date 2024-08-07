package dev.aries.sagehub.repository;

import dev.aries.sagehub.dto.search.GetCoursesPage;
import dev.aries.sagehub.model.Course;
import dev.aries.sagehub.specification.GeneralSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
	boolean existsByCode(String code);

	boolean existsByName(String name);

	default Page<Course> findAll(GetCoursesPage request, String status, Pageable page) {
		GeneralSpecification<Course> spec = new GeneralSpecification<>();
		return findAll(
				Specification
						.where(spec.hasName(request.name()))
						.and(spec.hasCode(request.code()))
						.and(spec.hasStatus(status)),
				page
		);
	}
}
