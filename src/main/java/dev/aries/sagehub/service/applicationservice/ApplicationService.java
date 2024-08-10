package dev.aries.sagehub.service.applicationservice;

import java.util.List;

import dev.aries.sagehub.dto.request.ApplicantInfoRequest;
import dev.aries.sagehub.dto.request.ProgramChoicesRequest;
import dev.aries.sagehub.dto.response.ApplicantInfoResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;

/**
 * The {@code ApplicationService} interface provides methods for managing applications.
 * It includes functionality for adding personal information, retrieving application details,
 * and updating program choices.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to application management.
 * </p>
 * @author Jacobs Agyei
 */
public interface ApplicationService {

	/**
	 * Adds profile information for a new applicant.
	 * <p>
	 * This method takes an {@code ApplicantInfoRequest} object containing the details of the applicant to be added,
	 * and returns an {@code ApplicationInfoResponse} object containing the details of the newly added applicant.
	 * </p>
	 * @param request the {@code ApplicantInfoRequest} containing the applicant's personal information.
	 * @return an {@code ApplicantInfoResponse} containing the new applicant's information.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	ApplicantInfoResponse addApplicantInfo(ApplicantInfoRequest request);

	/**
	 * Retrieves the profile information of an applicant.
	 * <p>
	 * This method takes the {@code id} of the application and returns an {@code ApplicantInfoResponse} object
	 * containing the applicant's details.
	 * </p>
	 * @param applicationId the ID of the applicant to be retrieved.
	 * @return an {@code ApplicantInfoResponse} containing the applicant's information.
	 * @throws IllegalArgumentException if the applicationId is null or invalid.
	 */
	ApplicantInfoResponse getApplicantInfo(Long applicationId);

	/**
	 * Updates the profile information for an applicant.
	 * <p>
	 * This method takes the ID of the application and an {@code ApplicantInfoRequest} object
	 * containing the updated personal information,
	 * and returns an {@code ApplicantInfoResponse} object containing the updated personal information.
	 * </p>
	 * @param applicationId      the ID of the application whose personal information is to be updated.
	 * @param request the {@code ApplicantInfoRequest} containing the updated personal information.
	 * @return an {@code ApplicantInfoResponse} containing the updated personal information.
	 * @throws IllegalArgumentException if the id or request is null or contains invalid data.
	 */
	ApplicantInfoResponse updateApplicantInfo(Long applicationId, ApplicantInfoRequest request);

	/**
	 * Updates the program choices for an applicant.
	 * <p>
	 * This method takes the ID of the application and a {@code ProgramChoicesRequest} object
	 * containing the updated program choices,
	 * and returns a list of {@code ProgramResponse} objects containing the updated program choices.
	 * </p>
	 * @param applicationId the ID of the application whose program choices are to be updated.
	 * @param request     the {@code ProgramChoicesRequest} containing the updated program choices.
	 * @return a list of {@code ProgramResponse} objects containing the updated program choices.
	 * @throws IllegalArgumentException if the applicationId or request is null or contains invalid data.
	 */
	List<ProgramResponse> updateApplicantProgramChoices(Long applicationId, ProgramChoicesRequest request);

}
