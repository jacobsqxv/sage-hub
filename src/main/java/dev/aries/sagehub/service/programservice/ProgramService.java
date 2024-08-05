package dev.aries.sagehub.service.programservice;

import dev.aries.sagehub.dto.request.ProgramRequest;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.dto.search.GetProgramsPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The {@code ProgramService} interface provides methods for managing programs.
 * It includes functionality for adding, retrieving, updating, and listing programs,
 * as well as updating program courses.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to program management.
 * </p>
 * @author Jacobs Agyei
 */
public interface ProgramService {

	/**
	 * Adds a new program.
	 * <p>
	 * This method takes a {@code ProgramRequest} object containing the details of the program to be added,
	 * and returns a {@code ProgramResponse} object containing the details of the newly added program.
	 * </p>
	 * @param request the {@code ProgramRequest} containing the program information.
	 * @return a {@code ProgramResponse} containing the new program's information.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	ProgramResponse addProgram(ProgramRequest request);

	/**
	 * Retrieves a paginated list of programs.
	 * <p>
	 * This method takes a {@code GetProgramsPage} request object and a {@code Pageable} object,
	 * and returns a {@code Page} of {@code ProgramResponse} objects containing the programs' details.
	 * </p>
	 * @param request the {@code GetProgramsPage} containing the pagination and filter information.
	 * @param pageable the {@code Pageable} object containing pagination information.
	 * @return a {@code Page} of {@code ProgramResponse} objects containing the programs' information.
	 * @throws IllegalArgumentException if the request or pageable is null or contains invalid data.
	 */
	Page<ProgramResponse> getPrograms(GetProgramsPage request, Pageable pageable);

	/**
	 * Retrieves the details of a program.
	 * <p>
	 * This method takes the ID of the program and returns a {@code ProgramResponse} object
	 * containing the program's details.
	 * </p>
	 * @param programId the ID of the program to be retrieved.
	 * @return a {@code ProgramResponse} containing the program's information.
	 * @throws IllegalArgumentException if the programId is null or invalid.
	 */
	ProgramResponse getProgram(Long programId);

	/**
	 * Updates the details of a program.
	 * <p>
	 * This method takes the ID of the program and a {@code ProgramRequest} object containing the updated details,
	 * and returns a {@code ProgramResponse} object containing the updated program details.
	 * </p>
	 * @param programId the ID of the program to be updated.
	 * @param request the {@code ProgramRequest} containing the updated program information.
	 * @return a {@code ProgramResponse} containing the updated program's information.
	 * @throws IllegalArgumentException if the programId or request is null or contains invalid data.
	 */
	ProgramResponse updateProgram(Long programId, ProgramRequest request);

}
