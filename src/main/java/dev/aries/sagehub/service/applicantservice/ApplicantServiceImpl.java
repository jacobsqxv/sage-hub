package dev.aries.sagehub.service.applicantservice;

import java.util.List;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.ApplicantRequest;
import dev.aries.sagehub.dto.request.ProgramChoicesRequest;
import dev.aries.sagehub.dto.response.ApplicantResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.enums.ApplicantStatus;
import dev.aries.sagehub.mapper.ApplicantMapper;
import dev.aries.sagehub.mapper.ProgramMapper;
import dev.aries.sagehub.model.Applicant;
import dev.aries.sagehub.model.BasicInfo;
import dev.aries.sagehub.model.ContactInfo;
import dev.aries.sagehub.model.EmergencyContact;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.Voucher;
import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.repository.ApplicantRepository;
import dev.aries.sagehub.service.basicinfoservice.BasicInfoInterface;
import dev.aries.sagehub.service.contactinfoservice.ContactInfoInterface;
import dev.aries.sagehub.service.emgcontactservice.EmergencyContactInterface;
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
	private final ApplicantRepository applicantRepository;
	private final ApplicantMapper applicantMapper;
	private final ProgramMapper programMapper;
	private final ApplicantUtil applicantUtil;
	private final ContactInfoInterface contactInfoInterface;
	private final EmergencyContactInterface emergencyContactInterface;
	private final BasicInfoInterface basicInfoInterface;
	private final Checks checks;
	private final Generators generators;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ApplicantResponse addPersonalInfo(ApplicantRequest request) {
		User user = checks.currentlyLoggedInUser();
		checks.checkApplicantExists(user.getId());
		Voucher voucher = applicantUtil.getVoucher(Long.valueOf(user.getUsername()));
		applicantUtil.updateVoucherStatus(voucher);
		BasicInfo basicInfo = basicInfoInterface.addBasicInfo(request.basicInfo(), user.getId());
		ContactInfo contactInfo = contactInfoInterface.addContactInfo(request.contactInfo(), user.getId());
		EmergencyContact emergencyContact = emergencyContactInterface
				.addEmergencyContact(request.guardianInfo(), user.getId());
		Email primaryEmail = generators.generateUserEmail(user.getUsername(), "STUDENT");
		Applicant applicant = Applicant.builder()
				.id(voucher.getSerialNumber())
				.primaryEmail(primaryEmail.value())
				.yearOfApplication(voucher.getAcademicYear())
				.basicInfo(basicInfo)
				.contactInfo(contactInfo)
				.emergencyContact(emergencyContact)
				.isSubmitted(false)
				.status(ApplicantStatus.PENDING)
				.user(user)
				.build();
		applicantRepository.save(applicant);
		return applicantMapper.toApplicantResponse(applicant);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicantResponse getApplicant(Long applicantId) {
		User loggedInUser = checks.currentlyLoggedInUser();
		applicantUtil.validApplicant(loggedInUser.getId(), applicantId);
		Applicant applicant = applicantUtil.loadApplicant(applicantId);
		return applicantMapper.toApplicantResponse(applicant);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public List<ProgramResponse> updateApplicantProgramChoices(Long applicantId, ProgramChoicesRequest request) {
		User loggedInUser = checks.currentlyLoggedInUser();
		applicantUtil.validApplicant(loggedInUser.getId(), applicantId);
		Applicant applicant = applicantUtil.loadApplicant(applicantId);
		List<Program> programChoices = request.programChoices()
				.stream().map(applicantUtil::getProgram).toList();
		if (programChoices.size() != 4) {
			throw new IllegalArgumentException(ExceptionConstants.PROGRAM_FETCH_ERROR);
		}
		applicant.getProgramChoices().clear();
		applicant.getProgramChoices().addAll(programChoices);
		applicantRepository.save(applicant);
		return programChoices.stream().map(programMapper::toBasicProgramResponse).toList();
	}

}
