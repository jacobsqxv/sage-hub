package dev.aries.sagehub.service.programservice;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.CrseOffrgRequest;
import dev.aries.sagehub.dto.request.ProgramRequest;
import dev.aries.sagehub.dto.response.CrseOffrgResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.dto.search.GetCrseOffrgPage;
import dev.aries.sagehub.dto.search.GetProgramsPage;
import dev.aries.sagehub.enums.Degree;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.mapper.CourseMapper;
import dev.aries.sagehub.mapper.ProgramMapper;
import dev.aries.sagehub.model.CourseOffering;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.AcademicPeriod;
import dev.aries.sagehub.repository.CourseOffrgRepository;
import dev.aries.sagehub.repository.DepartmentRepository;
import dev.aries.sagehub.repository.ProgramRepository;
import dev.aries.sagehub.strategy.UpdateStrategyConfig;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.DataLoader;
import dev.aries.sagehub.util.UserUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.aries.sagehub.constant.ExceptionConstants.ALREADY_EXISTS;

/**
 * Implementation of the {@code ProgramService} and {@code CourseOfferingService} interfaces.
 *
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.programservice.ProgramService
 * @see CourseOfferingService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService, CourseOfferingService {

	private static final String NAME = "Program";
	private final CourseOffrgRepository courseOffrgRepository;
	private final UpdateStrategyConfig updateStrategyConfig;
	private final DepartmentRepository departmentRepository;
	private final ProgramRepository programRepository;
	private final DataLoader dataLoader;
	private final UserUtil userUtil;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProgramResponse addProgram(ProgramRequest request) {
		existsByName(request.name().toUpperCase());
		Department department = dataLoader.loadDepartment(request.departmentId());
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
		return ProgramMapper.toResponse(program);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<ProgramResponse> getPrograms(GetProgramsPage request, Pageable pageable) {
		if (request.status() != null) {
			Checks.checkIfEnumExists(Status.class, request.status());
		}
		User loggedInUser = userUtil.currentlyLoggedInUser();
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
				.map(ProgramMapper::toResponse);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProgramResponse getProgram(Long programId) {
		Program program = dataLoader.loadProgram(programId);
		return ProgramMapper.toResponse(program);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProgramResponse updateProgram(Long programId, ProgramRequest request) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		Checks.checkAdmins(loggedInUser.getRole().getName());
		Program program = dataLoader.loadProgram(programId);
		Program updatedProgram = (Program) updateStrategyConfig
				.checkStrategy("Program").update(program, request);
		programRepository.save(updatedProgram);
		log.info("Program {} updated successfully", updatedProgram.getName());
		return ProgramMapper.toResponse(updatedProgram);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<CrseOffrgResponse> getCrseOffgForProgram(
			Long programId, GetCrseOffrgPage request, Pageable pageable) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		Program program = dataLoader.loadProgram(programId);
		if (Checks.isAdmin(loggedInUser.getRole().getName())) {
			return getAllCrseOffrgs(program, request, pageable);
		}
		return getActiveCrseOffrgs(program, request, pageable);
	}

	private Page<CrseOffrgResponse> getActiveCrseOffrgs(
			Program program, GetCrseOffrgPage request, Pageable pageable) {
		request = request.withStatus(Status.ACTIVE.name());
		return loadCourseOffrgs(program, request, pageable);
	}

	private Page<CrseOffrgResponse> getAllCrseOffrgs(
			Program program, GetCrseOffrgPage request, Pageable pageable) {
		return loadCourseOffrgs(program, request, pageable);
	}

	private Page<CrseOffrgResponse> loadCourseOffrgs(
			Program program, GetCrseOffrgPage request, Pageable pageable) {
		return courseOffrgRepository.findAll(program, request, pageable)
				.map(CourseMapper::toCourseOffrgResponse);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CrseOffrgResponse addCrseOffgForProgram(Long programId, CrseOffrgRequest request) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		Checks.checkAdmins(loggedInUser.getRole().getName());
		checkCourseOffering(programId, request.courseId(), request.period());
		CourseOffering courseOffering = CourseOffering.builder()
				.program(dataLoader.loadProgram(programId))
				.course(dataLoader.loadCourse(request.courseId()))
				.academicPeriod(new AcademicPeriod(
						(request.period().year()),
						request.period().semester()
				))
				.status(Status.PENDING)
				.build();
		courseOffrgRepository.save(courseOffering);
		return CourseMapper.toCourseOffrgResponse(courseOffering);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void deleteCourseConfig(Long programId, Long id) {
		int success = courseOffrgRepository.deleteByIdAndProgramId(id, programId);
		if (success != 1) {
			throw new IllegalArgumentException(
					String.format(ExceptionConstants.NOT_FOUND, "Course configuration"));
		}
		else {
			log.info("Course configuration deleted successfully");
		}
	}

	public void checkCourseOffering(Long programId, Long courseId, AcademicPeriod period) {
		boolean courseExistsAtPeriod = courseOffrgRepository.existsByProgramIdAndCourseIdAndAcademicPeriod(
				programId, courseId, period);
		boolean courseExists = courseOffrgRepository.existsByProgramIdAndCourseId(programId, courseId);
		if (courseExistsAtPeriod || courseExists) {
			throw new EntityExistsException(
					String.format(ALREADY_EXISTS, "Course configuration"));
		}
	}

	private void existsByName(String name) {
		if (programRepository.existsByName(name)) {
			throw new IllegalArgumentException(
					String.format(ExceptionConstants.NAME_EXISTS, NAME, name));
		}
	}
}
