package dev.aries.sagehub.strategy;

import java.util.Objects;
import java.util.Optional;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.CourseRequest;
import dev.aries.sagehub.model.Course;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateCourse implements UpdateStrategy<Course, CourseRequest> {

	@Override
	public Course update(Course entity, CourseRequest request) {
		checkName(entity, request);
		Optional.ofNullable(request.description()).ifPresent(entity::setDescription);
		Optional.ofNullable(request.creditUnits()).ifPresent(entity::setCreditUnits);
		Optional.ofNullable(request.status()).ifPresent(entity::setStatus);
		return entity;
	}

	private void checkName(Course entity, CourseRequest request) {
		if (!Objects.equals(request.name(), entity.getName())) {
			log.info("Entity: {}, Request: {}", entity.getName(), request.name());
			throw new IllegalArgumentException(String.format(
					ExceptionConstants.CANNOT_UPDATE_NAME, "Course"));
		}
	}
}
