package dev.aries.sagehub.service.programservice;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.ProgramCourseRequest;
import dev.aries.sagehub.dto.request.ProgramRequest;
import dev.aries.sagehub.dto.response.ProgramCourseResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.dto.search.GetPrgCoursesPage;
import dev.aries.sagehub.dto.search.GetProgramsPage;
import dev.aries.sagehub.enums.Degree;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.mapper.ProgramCourseMapper;
import dev.aries.sagehub.mapper.ProgramMapper;
import dev.aries.sagehub.model.AcademicPeriod;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.model.ProgramCourse;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.DepartmentRepository;
import dev.aries.sagehub.repository.ProgramCourseRepository;
import dev.aries.sagehub.repository.ProgramRepository;
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.GlobalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@code ProgramService} and {@code ProgramCourseService} interfaces.
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.programservice.ProgramService
 * @see dev.aries.sagehub.service.programservice.ProgramCourseService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService, ProgramCourseService {

	private final ProgramRepository programRepository;
	private final ProgramCourseRepository programCourseRepository;
	private final DepartmentRepository departmentRepository;
	private final ProgramMapper programMapper;
	private final ProgramCourseMapper programCourseMapper;
	private final GlobalUtil globalUtil;
	private final Checks checks;
	private static final String NAME = "Program";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProgramResponse addProgram(ProgramRequest request) {
		existsByName(request.name().toUpperCase());
		Department department = globalUtil.loadDepartment(request.departmentId());
		Degree degree = request.degree();
		Program program = Program.builder()
				.name(request.name().toUpperCase())
				.description(request.description())
				.department(department)
				.cutOff(request.cutOff())
				.duration(request.duration())
				.degree(degree)
				.status(Status.PENDING)
				.build();
		programRepository.save(program);
		if (!department.getPrograms().contains(program)) {
			department.getPrograms().add(program);
		}
		departmentRepository.save(department);
		return programMapper.toProgramResponse(program);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<ProgramResponse> getPrograms(GetProgramsPage request, Pageable pageable) {
		if (request.status() != null) {
			Checks.checkIfEnumExists(Status.class, request.status());
		}
		User loggedInUser = checks.currentlyLoggedInUser();
		if (Checks.isAdmin(loggedInUser.getRole().getName())) {
			return getAllPrograms(request, pageable);
		}
		return getActivePrograms(request, pageable);
	}

	private Page<ProgramResponse> getActivePrograms(GetProgramsPage request, Pageable pageable) {
		return loadPrograms(request, Status.ACTIVE.name(), pageable);
	}

	private Page<ProgramResponse> getAllPrograms(GetProgramsPage request, Pageable pageable) {
		return loadPrograms(request, request.status(), pageable);
	}

	private Page<ProgramResponse> loadPrograms(GetProgramsPage request, String status, Pageable pageable) {
		return programRepository.findAll(request, status, pageable)
				.map(programMapper::toProgramResponse);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProgramResponse getProgram(Long programId) {
		Program program = globalUtil.loadProgram(programId);
		return programMapper.toProgramResponse(program);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProgramResponse updateProgram(Long programId, ProgramRequest request) {
		User loggedInUser = checks.currentlyLoggedInUser();
		checks.checkAdmins(loggedInUser.getRole().getName());
		Program program = globalUtil.loadProgram(programId);
		UpdateStrategy updateStrategy = globalUtil.checkStrategy("updateProgram");
		program = (Program) updateStrategy.update(program, request);
		programRepository.save(program);
		log.info("Program {} updated successfully", program.getName());
		return programMapper.toProgramResponse(program);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<ProgramCourseResponse> getProgramCourses(
			Long programId, GetPrgCoursesPage request, Pageable pageable) {
		User loggedInUser = checks.currentlyLoggedInUser();
		Program program = globalUtil.loadProgram(programId);
		if (Checks.isAdmin(loggedInUser.getRole().getName())) {
			return getAllProgramsCourses(program, request, pageable);
		}
		return getActiveProgramCourses(program, request, pageable);
	}

	private Page<ProgramCourseResponse> getActiveProgramCourses(
			Program program, GetPrgCoursesPage request, Pageable pageable) {
		request = request.withStatus(Status.ACTIVE.name());
		return loadProgramCourses(program, request, pageable);
	}

	private Page<ProgramCourseResponse> getAllProgramsCourses(
			Program program, GetPrgCoursesPage request, Pageable pageable) {
		return loadProgramCourses(program, request, pageable);
	}

	private Page<ProgramCourseResponse> loadProgramCourses(
			Program program, GetPrgCoursesPage request, Pageable pageable) {
		return programCourseRepository.findAll(program, request, pageable)
				.map(programCourseMapper::toProgramCourseResponse);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProgramCourseResponse addProgramCourse(Long programId, ProgramCourseRequest request) {
		User loggedInUser = checks.currentlyLoggedInUser();
		checks.checkAdmins(loggedInUser.getRole().getName());
		checks.checkProgramCourse(programId, request.courseId(), request.period());
		ProgramCourse programCourse = ProgramCourse.builder()
				.program(globalUtil.loadProgram(programId))
				.course(globalUtil.loadCourse(request.courseId()))
				.academicPeriod(new AcademicPeriod(
						(request.period().year()),
						request.period().semester()
				))
				.status(Status.PENDING)
				.build();
		programCourseRepository.save(programCourse);
		return programCourseMapper.toProgramCourseResponse(programCourse);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void deleteCourseConfig(Long programId, Long id) {
		int success = programCourseRepository.deleteByIdAndProgramId(id, programId);
		if (success != 1) {
			throw new IllegalArgumentException(
					String.format(ExceptionConstants.NOT_FOUND, "Course configuration"));
		}
		else {
			log.info("Course configuration deleted successfully");
		}
	}

	private void existsByName(String name) {
		if (programRepository.existsByName(name)) {
			throw new IllegalArgumentException(
					String.format(ExceptionConstants.NAME_EXISTS, NAME, name));
		}
	}
}
