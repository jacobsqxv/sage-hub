package dev.aries.sagehub.service.programservice;

import java.util.List;

import dev.aries.sagehub.dto.request.ProgramRequest;
import dev.aries.sagehub.dto.response.ProgramResponse;

public interface ProgramService {
	ProgramResponse addProgram(ProgramRequest request);

	List<ProgramResponse> getPrograms();

	ProgramResponse getProgram(Long programId);

	ProgramResponse updateProgram(Long programId, ProgramRequest request);

	ProgramResponse archiveProgram(Long programId);
}
