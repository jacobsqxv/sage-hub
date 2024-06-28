package dev.aries.sagehub.strategy;

import java.util.Objects;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.ProgramRequest;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.util.Checks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateProgram implements UpdateStrategy<Program, ProgramRequest> {
	private final Checks checks;

	@Override
	public Program update(Program entity, ProgramRequest request) {
		checkName(entity, request);
		entity.setDescription(request.description() != null ? request.description() : entity.getDescription());
		this.checks.checkIfEnumExists(Status.class, request.status());
		entity.setStatus(Status.valueOf(request.status()));
		return entity;
	}

	private void checkName(Program entity, ProgramRequest request) {
		if (!Objects.equals(request.name(), entity.getName())) {
			log.info("INFO - Entity: {}, Request: {}", entity.getName(), request.name());
			throw new IllegalArgumentException(String.format(ExceptionConstants.CANNOT_UPDATE_NAME, "Program"));
		}
	}
}
