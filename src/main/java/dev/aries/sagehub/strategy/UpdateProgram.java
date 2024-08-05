package dev.aries.sagehub.strategy;

import java.util.Objects;
import java.util.Optional;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.ProgramRequest;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import static dev.aries.sagehub.constant.ExceptionConstants.NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateProgram implements UpdateStrategy<Program, ProgramRequest> {
	private final DepartmentRepository departmentRepository;
	private final UpdateStatus updateStatus;

	@Override
	public Program update(Program entity, ProgramRequest request) {
		checkName(entity, request);
		Department department = checkDepartment(request.departmentId());
		Optional.ofNullable(request.description()).ifPresent(entity::setDescription);
		Optional.ofNullable(request.duration()).ifPresent(entity::setDuration);
		Optional.ofNullable(request.cutOff()).ifPresent(entity::setCutOff);
		Optional.ofNullable(request.degree()).ifPresent(entity::setDegree);
		entity.setDepartment(department);
		Optional.ofNullable(request.status()).ifPresent(entity::setStatus);
		updateStatus.updateProgramCoursesStatus(request.status(), entity.getId());
		log.info("Program status updated successfully: {}", entity.getStatus());
		return entity;
	}


	private Department checkDepartment(Long id) {
		return departmentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(NOT_FOUND, "Department")));
	}

	private void checkName(Program entity, ProgramRequest request) {
		if (!Objects.equals(request.name(), entity.getName())) {
			log.info("Entity: {}, Request: {}", entity.getName(), request.name());
			throw new IllegalArgumentException(String.format(
					ExceptionConstants.CANNOT_UPDATE_NAME, "Program"));
		}
	}
}
