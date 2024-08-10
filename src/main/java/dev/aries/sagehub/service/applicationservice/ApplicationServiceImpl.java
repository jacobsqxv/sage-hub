package dev.aries.sagehub.service.applicantservice;

import java.util.List;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.ApplicationRequest;
import dev.aries.sagehub.dto.request.ProgramChoicesRequest;
import dev.aries.sagehub.dto.response.ApplicationResponse;
import dev.aries.sagehub.dto.response.BasicApplicationResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.enums.ApplicationStatus;
import dev.aries.sagehub.mapper.ApplicationMapper;
import dev.aries.sagehub.mapper.ProgramMapper;
import dev.aries.sagehub.model.Application;
import dev.aries.sagehub.model.UserInfo;
import dev.aries.sagehub.model.attribute.ContactInfo;
import dev.aries.sagehub.model.EmergencyInfo;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.Voucher;
import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.repository.ApplicationRepository;
import dev.aries.sagehub.service.userinfoservice.UserInfoService;
import dev.aries.sagehub.service.contactinfoservice.ContactInfoInterface;
import dev.aries.sagehub.service.emergencyinfoservice.EmergencyInfoService;
import dev.aries.sagehub.util.ApplicantUtil;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.Generators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Implementation of the {@code ApplicantService} interface.
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.applicantservice.ApplicantService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {
	private final ApplicationRepository applicationRepository;
	private final ApplicationMapper applicationMapper;
	private final ProgramMapper programMapper;
	private final ApplicantUtil applicantUtil;
	private final ContactInfoInterface contactInfoInterface;
	private final EmergencyInfoService emergencyInfoService;
	private final UserInfoService userInfoService;
	private final Checks checks;
	private final Generators generators;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public BasicApplicationResponse addPersonalInfo(ApplicationRequest request) {
		User user = checks.currentlyLoggedInUser();
		checks.checkApplicantExists(user.getId());
		Voucher voucher = applicantUtil.getVoucher(Long.valueOf(user.getUsername()));
		applicantUtil.updateVoucherStatus(voucher);
		UserInfo userInfo = userInfoService.addBasicInfo(request.applicantInfo(), user.getId());
		ContactInfo contactInfo = contactInfoInterface.addContactInfo(request.contactInfo(), user.getId());
		EmergencyInfo EmergencyInfo = emergencyInfoService
				.addEmergencyInfo(request.guardianInfo(), user.getId());
		Email primaryEmail = generators.generateUserEmail(user.getUsername(), "STUDENT");
		Application application = Application.builder()
				.id(voucher.getSerialNumber())
				.primaryEmail(primaryEmail.value())
				.yearOfApplication(voucher.getAcademicYear())
				.basicInfo(userInfo)
				.contactInfo(contactInfo)
				.emergencyContact(EmergencyInfo)
				.isSubmitted(false)
				.status(ApplicationStatus.PENDING)
				.user(user)
				.build();
		applicationRepository.save(application);
		return applicationMapper.toBasicResponse(application);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationResponse getApplicant(Long applicantId) {
		User loggedInUser = checks.currentlyLoggedInUser();
		Checks.validateLoggedInUserName(loggedInUser, applicantId);
		Application application = applicantUtil.loadApplicant(applicantId);
		return applicationMapper.toResponse(application);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public List<ProgramResponse> updateApplicantProgramChoices(Long applicantId, ProgramChoicesRequest request) {
		User loggedInUser = checks.currentlyLoggedInUser();
		Checks.validateLoggedInUserName(loggedInUser, applicantId);
		Application application = applicantUtil.loadApplicant(applicantId);
		List<Program> programChoices = request.programChoices()
				.stream().map(applicantUtil::getProgram).toList();
		if (programChoices.size() != 4) {
			throw new IllegalArgumentException(ExceptionConstants.PROGRAM_FETCH_ERROR);
		}
		application.getProgramChoices().clear();
		application.getProgramChoices().addAll(programChoices);
		applicationRepository.save(application);
		return programChoices.stream().map(programMapper::toBasicResponse).toList();
	}

}
