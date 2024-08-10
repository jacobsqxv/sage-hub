package dev.aries.sagehub.service.applicantservice;

import java.util.List;

import dev.aries.sagehub.dto.request.ApplicationRequest;
import dev.aries.sagehub.dto.request.ProgramChoicesRequest;
import dev.aries.sagehub.dto.response.ApplicationResponse;
import dev.aries.sagehub.dto.response.BasicApplicationResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;

/**
 * The {@code ApplicantService} interface provides methods for managing applicants.
 * It includes functionality for adding personal information, retrieving applicant details,
 * and updating program choices.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to applicant management.
 * </p>
 * @author Jacobs Agyei
 */
public interface ApplicantService {

	/**
	 * Adds personal information for a new applicant.
	 * <p>
	 * This method takes an {@code ApplicantRequest} object containing the details of the applicant to be added,
	 * and returns an {@code BasicApplicantResponse} object containing the details of the newly added applicant.
	 * </p>
	 * @param request the {@code ApplicantRequest} containing the applicant's personal information.
	 * @return an {@code BasicApplicantResponse} containing the new applicant's information.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	BasicApplicationResponse addPersonalInfo(ApplicationRequest request);

	/**
	 * Retrieves the details of an applicant.
	 * <p>
	 * This method takes the {@code id} of the applicant and returns an {@code ApplicantResponse} object
	 * containing the applicant's details.
	 * </p>
	 * @param applicantId the ID of the applicant to be retrieved.
	 * @return an {@code ApplicantResponse} containing the applicant's information.
	 * @throws IllegalArgumentException if the applicantId is null or invalid.
	 */
	ApplicationResponse getApplicant(Long applicantId);

	/**
	 * Updates the program choices for an applicant.
	 * <p>
	 * This method takes the ID of the applicant and a {@code ProgramChoicesRequest} object
	 * containing the updated program choices,
	 * and returns a list of {@code ProgramResponse} objects containing the updated program choices.
	 * </p>
	 * @param applicantId the ID of the applicant whose program choices are to be updated.
	 * @param request     the {@code ProgramChoicesRequest} containing the updated program choices.
	 * @return a list of {@code ProgramResponse} objects containing the updated program choices.
	 * @throws IllegalArgumentException if the applicantId or request is null or contains invalid data.
	 */
	List<ProgramResponse> updateApplicantProgramChoices(Long applicantId, ProgramChoicesRequest request);
}
