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
				program.getDescription(),
				"No courses found for this program",
				program.getStatus().getValue()
		);
	}
}
