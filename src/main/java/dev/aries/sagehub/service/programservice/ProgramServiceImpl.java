package dev.aries.sagehub.service.programservice;

import java.util.List;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.ProgramRequest;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.mapper.ProgramMapper;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.repository.ProgramRepository;
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.GlobalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import static dev.aries.sagehub.constant.ExceptionConstants.NAME_EXISTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

	private final ProgramRepository programRepository;
	private final ProgramMapper programMapper;
	private final GlobalUtil globalUtil;
	private final Checks checks;
	public static final String NAME = "Program";

	@Override
	public ProgramResponse addProgram(ProgramRequest request) {
		existsByName(request.name().toUpperCase());
		Program program = Program.builder()
				.name(request.name().toUpperCase())
				.description(request.description())
				.status(Status.PENDING_REVIEW)
				.build();
		this.programRepository.save(program);
		return this.programMapper.toProgramResponse(program);
	}

	@Override
	public List<ProgramResponse> getPrograms() {
		List<Program> programs = this.programRepository.findAll();
		return programs.stream().map(this.programMapper::toProgramResponse).toList();
	}

	@Override
	public ProgramResponse getProgram(Long programId) {
		Program program = loadProgram(programId);
		return this.programMapper.toProgramResponse(program);
	}

	@Override
	public ProgramResponse updateProgram(Long programId, ProgramRequest request) {
		this.checks.isAdmin();
		Program program = loadProgram(programId);
		UpdateStrategy updateStrategy = this.globalUtil.checkStrategy("updateProgram");
		program = (Program) updateStrategy.update(program, request);
		this.programRepository.save(program);
		log.info("INFO - Program {} updated successfully", program.getName());
		return this.programMapper.toProgramResponse(program);
	}

	@Override
	public ProgramResponse archiveProgram(Long programId) {
		this.checks.isAdmin();
		Program program = loadProgram(programId);
		program.setStatus(Status.ARCHIVED);
		this.programRepository.save(program);
		return this.programMapper.toProgramResponse(program);
	}

	private Program loadProgram(Long programId) {
		return programRepository.findById(programId)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, NAME)));
	}

	private void existsByName(String name) {
		if (programRepository.existsByName(name)) {
			throw new IllegalArgumentException(
					String.format(NAME_EXISTS, NAME, name));
		}
	}
}
