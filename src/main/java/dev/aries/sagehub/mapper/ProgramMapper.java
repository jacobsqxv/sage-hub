package dev.aries.sagehub.mapper;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.model.Program;

public final class ProgramMapper {

	public static ProgramResponse toResponse(Program program) {
		return new ProgramResponse(
				program.getId(),
				program.getName(),
				program.getDepartment().getName(),
				program.getDegree().toString(),
				program.getDescription(),
				program.getDuration(),
				program.getCutOff(),
				program.getStatus().toString()
		);
	}

	public static ProgramResponse toBasicResponse(Program program) {
		return ProgramResponse.builder()
				.id(program.getId())
				.name(program.getName())
				.degree(program.getDegree().toString())
				.duration(program.getDuration())
				.build();
	}

	private ProgramMapper() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
