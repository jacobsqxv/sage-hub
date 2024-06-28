package dev.aries.sagehub.strategy;

import java.util.Objects;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.CourseRequest;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.model.Course;
import dev.aries.sagehub.util.Checks;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCourse implements UpdateStrategy<Course, CourseRequest> {

	private final Checks checks;

	@Override
	public Course update(Course entity, CourseRequest request) {
		checkName(entity, request);
		entity.setDescription(request.description() != null ? request.description() : entity.getDescription());
		entity.setCreditUnits(request.creditUnits() != null ? request.creditUnits() : entity.getCreditUnits());
		this.checks.checkIfEnumExists(Status.class, request.status());
		entity.setStatus(Status.valueOf(request.status()));
		return null;
	}

	private void checkName(Course entity, CourseRequest request) {
		if (!Objects.equals(request.name(), entity.getName())) {
			throw new IllegalArgumentException(String.format(ExceptionConstants.CANNOT_UPDATE_NAME, "Course"));
		}
	}
}
