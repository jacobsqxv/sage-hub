package dev.aries.sagehub.repository;

import dev.aries.sagehub.model.Course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
