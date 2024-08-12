package dev.aries.sagehub.service.applicationservice;

import java.util.List;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.ApplicantInfoRequest;
import dev.aries.sagehub.dto.request.ProgramChoicesRequest;
import dev.aries.sagehub.dto.request.UserProfileRequest;
import dev.aries.sagehub.dto.response.ApplicantInfoResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.enums.TokenStatus;
import dev.aries.sagehub.factory.ModelFactory;
import dev.aries.sagehub.mapper.ApplicationMapper;
import dev.aries.sagehub.mapper.ProgramMapper;
import dev.aries.sagehub.model.Application;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.UserProfile;
import dev.aries.sagehub.model.Voucher;
import dev.aries.sagehub.model.attribute.Education;
import dev.aries.sagehub.repository.ApplicationRepository;
import dev.aries.sagehub.repository.UserProfileRepository;
import dev.aries.sagehub.repository.VoucherRepository;
import dev.aries.sagehub.strategy.UpdateAttributes;
import dev.aries.sagehub.strategy.UpdateStrategyConfig;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.DataLoader;
import dev.aries.sagehub.util.UserUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.aries.sagehub.constant.ExceptionConstants.ALREADY_EXISTS;

/**
 * Implementation of the {@code ApplicationService} interface.
 *
 * @author Jacobs Agyei
 * @see ApplicationService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
	private final UserProfileRepository userProfileRepository;
	private final UpdateStrategyConfig updateStrategyConfig;
	private final VoucherRepository voucherRepository;
	private final ApplicationRepository appRepository;
	private final ModelFactory modelFactory;
	private final DataLoader dataLoader;
	private final UserUtil userUtil;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ApplicantInfoResponse addApplicantInfo(ApplicantInfoRequest request) {
		User user = userUtil.currentlyLoggedInUser();
		checkApplicantExists(user.getId());
		Voucher voucher = dataLoader.loadVoucher(Long.valueOf(user.getUsername()));
		updateVoucherStatus(voucher);
		Application newApplication = modelFactory.createNewApplication(request, user, voucher);
		appRepository.save(newApplication);
		return ApplicationMapper.toApplicantInfoResponse(newApplication);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicantInfoResponse getApplicantInfo(Long applicationId) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		Application application = dataLoader.loadApplicationById(applicationId);
		Checks.validateLoggedInUser(loggedInUser, application.getApplicant().getUser());
		return ApplicationMapper.toApplicantInfoResponse(application);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicantInfoResponse updateApplicantInfo(Long applicationId, ApplicantInfoRequest request) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		Application application = dataLoader.loadApplicationById(applicationId);
		Checks.validateLoggedInUser(loggedInUser, application.getApplicant().getUser());
		UserProfile userProfile = application.getApplicant().getUserProfile();
		UserProfileRequest userProfileRequest = new UserProfileRequest(
				request.applicantInfo().personalInfo(),
				request.applicantInfo().contactInfo()
		);
		UserProfile updatedProfile = (UserProfile) updateStrategyConfig
				.checkStrategy("UserProfile")
				.update(userProfile, userProfileRequest);
		userProfileRepository.save(updatedProfile);
		Education updatedEducation = UpdateAttributes
				.updateEducation(application.getEducation(), request.educationBackground());
		application.setEducation(updatedEducation);
		appRepository.save(application);
		return ApplicationMapper.toApplicantInfoResponse(application);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public List<ProgramResponse> updateApplicantProgramChoices(Long applicationId, ProgramChoicesRequest request) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		Application application = dataLoader.loadApplicationById(applicationId);
		Checks.validateLoggedInUser(loggedInUser, application.getApplicant().getUser());
		List<Program> programChoices = request.programChoices()
				.stream().map(dataLoader::loadProgram).toList();
		if (programChoices.size() != 4) {
			throw new IllegalArgumentException(ExceptionConstants.PROGRAM_FETCH_ERROR);
		}
		application.getProgramChoices().clear();
		application.getProgramChoices().addAll(programChoices);
		appRepository.save(application);
		return programChoices.stream().map(ProgramMapper::toBasicResponse).toList();
	}

	public void checkApplicantExists(Long userId) {
		if (appRepository.existsByApplicantUserId(userId)) {
			throw new EntityExistsException(
					String.format(ALREADY_EXISTS, "Applicant"));
		}
	}

	public void updateVoucherStatus(Voucher voucher) {
		voucher.setStatus(TokenStatus.USED);
		voucherRepository.save(voucher);
	}

}
