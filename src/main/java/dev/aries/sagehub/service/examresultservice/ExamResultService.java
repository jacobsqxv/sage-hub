package dev.aries.sagehub.service.examresultservice;

import java.util.List;

import dev.aries.sagehub.dto.request.ExamResultRequest;
import dev.aries.sagehub.dto.response.ExamResultResponse;

/**
 * The {@code ExamResultService} interface provides methods for managing exam results.
 * It includes functionality for adding and updating exam results.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to exam result management.
 * </p>
 *
 * @author Jacobs Agyei
 */
public interface ExamResultService {
	/**
	 * Adds new exam results to the owner of the resource by their {@code id}.
	 * <p>
	 * This method takes the {@code id} and an {@code ExamResultRequest} object
	 * containing the details of the exam results to be added,
	 * and returns an {@code ExamResultsResponse} object containing the details of the newly added results.
	 * </p>
	 * @param id the unique identifier of the owner of the {@code ExamResult} object.
	 * @param request the {@code ExamResultRequest} containing the exam results information.
	 * @return an {@code ExamResultResponse} containing the new exam results information.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	ExamResultResponse addExamResults(Long id, ExamResultRequest request);

	/**
	 * Retrieves all exam results belonging to the owner of the resource by their {@code id}.
	 * <p>
	 * This method takes the {@code id} and returns a list of {@code ExamResultResponse}
	 * objects containing the details of the exam results.
	 * </p>
	 * @param id the unique identifier of the owner of the {@code ExamResult} object.
	 * @return a list of {@code ExamResultResponse} containing the exam results information.
	 */
	List<ExamResultResponse> getExamResults(Long id);

	/**
	 * Retrieves exam results of the owner of the resource by their {@code id}
	 * <p>
	 * This method takes an {@code id} and a {@code resultId} and returns an
	 * {@code ExamResultsResponse} object containing the details of the exam results.
	 * </p>
	 * @param id the unique identifier of the owner of the {@code ExamResult} object.
	 * @param resultId the ID of the exam results.
	 * @return an {@code ExamResultResponse} containing the exam results information.
	 */
	ExamResultResponse getExamResult(Long id, Long resultId);

	/**
	 * Updates existing exam results of the owner of the resource by their {@code id}.
	 * <p>
	 * This method takes an {@code id}, the {@code id} of the result and an {@code ExamResultRequest}
	 * object containing the updated details of the
	 * exam results, and returns an {@code ExamResultsResponse} object containing the updated results.
	 * </p>
	 * @param id the unique identifier of the owner of the {@code ExamResult} object.
	 * @param resultId the ID of the exam results to be updated.
	 * @param request the {@code ExamResultRequest} containing the updated exam results information.
	 * @return an {@code ExamResultResponse} containing the updated exam results information.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	ExamResultResponse updateExamResults(Long id, Long resultId, ExamResultRequest request);

	/**
	 * Deletes exam results for the owner of the resource by their {@code id}.
	 * <p>
	 * This method takes the {@code id} and {@code resultId} and deletes the exam results.
	 * </p>
	 * @param id the unique identifier of the owner of the {@code ExamResult} object.
	 * @param resultId the ID of the exam results.
	 * @throws IllegalArgumentException if the exam or result does not exist.
	 */
	void deleteExamResults(Long id, Long resultId);
}
