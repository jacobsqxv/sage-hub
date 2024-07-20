package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.model.Program;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProgramMapper {

	public ProgramResponse toProgramResponse(Program program) {
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

	public ProgramResponse toBasicProgramResponse(Program program) {
		return ProgramResponse.builder()
				.id(program.getId())
				.name(program.getName())
				.degree(program.getDegree().toString())
				.duration(program.getDuration())
				.build();
	}
}
