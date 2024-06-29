package dev.aries.sagehub.service.programservice;

import java.util.List;

import dev.aries.sagehub.dto.request.ProgramCourseRequest;
import dev.aries.sagehub.dto.request.ProgramRequest;
import dev.aries.sagehub.dto.response.ProgramCourseResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.enums.Semester;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.enums.Year;
import dev.aries.sagehub.mapper.ProgramCourseMapper;
import dev.aries.sagehub.mapper.ProgramMapper;
import dev.aries.sagehub.model.AcademicPeriod;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.model.ProgramCourse;
import dev.aries.sagehub.repository.DepartmentRepository;
import dev.aries.sagehub.repository.ProgramCourseRepository;
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
	private final ProgramCourseRepository programCourseRepository;
	private final DepartmentRepository departmentRepository;
	private final ProgramMapper programMapper;
	private final ProgramCourseMapper programCourseMapper;
	private final GlobalUtil globalUtil;
	private final Checks checks;
	public static final String NAME = "Program";

	@Override
	public ProgramResponse addProgram(ProgramRequest request) {
		existsByName(request.name().toUpperCase());
		Department department = this.globalUtil.loadDepartment(request.departmentId());
		Program program = Program.builder()
				.name(request.name().toUpperCase())
				.description(request.description())
				.department(department)
				.status(Status.PENDING_REVIEW)
				.build();
		this.programRepository.save(program);
		if (!department.getPrograms().contains(program)) {
			department.getPrograms().add(program);
		}
		this.departmentRepository.save(department);
		return this.programMapper.toProgramResponse(program);
	}

	@Override
	public List<ProgramResponse> getPrograms() {
		List<Program> programs = this.programRepository.findAll();
		return programs.stream().map(this.programMapper::toProgramResponse).toList();
	}

	@Override
	public ProgramResponse getProgram(Long programId) {
		Program program = this.globalUtil.loadProgram(programId);
		return this.programMapper.toProgramResponse(program);
	}

	@Override
	public ProgramResponse updateProgram(Long programId, ProgramRequest request) {
		this.checks.isAdmin();
		Program program = this.globalUtil.loadProgram(programId);
		UpdateStrategy updateStrategy = this.globalUtil.checkStrategy("updateProgram");
		program = (Program) updateStrategy.update(program, request);
		this.programRepository.save(program);
		log.info("INFO - Program {} updated successfully", program.getName());
		return this.programMapper.toProgramResponse(program);
	}

	private ProgramCourseResponse addProgramCourses(Long programId, ProgramCourseRequest request) {
		this.checks.isAdmin();
		ProgramCourse programCourse = ProgramCourse.builder()
				.program(this.globalUtil.loadProgram(programId))
				.course(this.globalUtil.loadCourse(request.courseId()))
				.academicPeriod(new AcademicPeriod(
						(request.period().year()),
						request.period().semester()
				))
				.status(Status.PENDING_REVIEW)
				.build();
		this.programCourseRepository.save(programCourse);
		return this.programCourseMapper.toProgramCourseResponse(programCourse);
	}

	@Override
	public ProgramCourseResponse updateProgramCourses(Long programId, ProgramCourseRequest request) {
		this.checks.isAdmin();
		ProgramCourse programCourse = this.globalUtil.loadProgramCourses(programId, request.courseId(), request.period());
		if (programCourse == null) {
			return addProgramCourses(programId, request);
		}
		UpdateStrategy updateStrategy = this.globalUtil.checkStrategy("updateProgramCourse");
		programCourse = (ProgramCourse) updateStrategy.update(programCourse, request);
		this.programCourseRepository.save(programCourse);
		return this.programCourseMapper.toProgramCourseResponse(programCourse);
	}

	private void existsByName(String name) {
		if (programRepository.existsByName(name)) {
			throw new IllegalArgumentException(
					String.format(NAME_EXISTS, NAME, name));
		}
	}
}
