package dev.aries.sagehub.strategy;

import java.util.Objects;
import java.util.Optional;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.ProgramRequest;
import dev.aries.sagehub.enums.Degree;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.repository.DepartmentRepository;
import dev.aries.sagehub.util.Checks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import static dev.aries.sagehub.constant.ExceptionConstants.NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateProgram implements UpdateStrategy<Program, ProgramRequest> {
	private final Checks checks;
	private final DepartmentRepository departmentRepository;
	private final UpdateStatus updateStatus;

	@Override
	public Program update(Program entity, ProgramRequest request) {
		checkName(entity, request);
		this.checks.checkIfEnumExists(Status.class, request.status());
		this.checks.checkIfEnumExists(Degree.class, request.degree());
		Department department = checkDepartment(request.departmentId());
		Optional.ofNullable(request.description()).ifPresent(entity::setDescription);
		Optional.ofNullable(request.duration()).ifPresent(entity::setDuration);
		Optional.ofNullable(request.cutOff()).ifPresent(entity::setCutOff);
		entity.setDegree(Degree.valueOf(request.degree()));
		entity.setDepartment(department);
		entity.setStatus(Status.valueOf(request.status()));
		this.updateStatus.updateProgramCoursesStatus(request.status(), entity.getId());
		log.info("INFO - Program status updated successfully: {}", entity.getStatus());
		return entity;
	}


	private Department checkDepartment(Long id) {
		return this.departmentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(String.format(NOT_FOUND, "Department")));
	}

	private void checkName(Program entity, ProgramRequest request) {
		if (!Objects.equals(request.name(), entity.getName())) {
			log.info("INFO - Entity: {}, Request: {}", entity.getName(), request.name());
			throw new IllegalArgumentException(String.format(ExceptionConstants.CANNOT_UPDATE_NAME, "Program"));
		}
	}
}
