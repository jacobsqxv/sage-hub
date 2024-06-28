package dev.aries.sagehub.strategy;

import java.util.List;
import java.util.Objects;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.DepartmentRequest;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.model.ProgramCourse;
import dev.aries.sagehub.repository.ProgramCourseRepository;
import dev.aries.sagehub.util.Checks;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateDepartment implements UpdateStrategy<Department, DepartmentRequest> {
	private final ProgramCourseRepository programCourseRepository;
	private final Checks checks;

	@Override
	public Department update(Department entity, DepartmentRequest request) {
		checkName(entity, request);
		this.checks.checkIfEnumExists(Status.class, request.status());
		entity.setStatus(Status.valueOf(request.status()));
		if (request.programIds() != null && !request.programIds().isEmpty()) {
			List<ProgramCourse> programCourses = programCourseRepository.findAllById(request.programIds());
			if (programCourses.size() != request.programIds().size()) {
				throw new EntityNotFoundException(ExceptionConstants.PROGRAM_FETCH_ERROR);
			}
			entity.setPrograms(programCourses);
		}
		return entity;
	}

	private void checkName(Department entity, DepartmentRequest request) {
		if (!Objects.equals(request.name(), entity.getName())) {
			throw new IllegalArgumentException(String.format(ExceptionConstants.CANNOT_UPDATE_NAME, "Department"));
		}
	}
}
