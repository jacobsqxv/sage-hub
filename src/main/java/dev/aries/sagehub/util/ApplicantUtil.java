package dev.aries.sagehub.util;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.enums.TokenStatus;
import dev.aries.sagehub.exception.UnauthorizedAccessException;
import dev.aries.sagehub.model.Applicant;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.model.Voucher;
import dev.aries.sagehub.repository.ApplicantRepository;
import dev.aries.sagehub.repository.ProgramRepository;
import dev.aries.sagehub.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicantUtil {
	private final VoucherRepository voucherRepository;
	private final ProgramRepository programRepository;
	private final ApplicantRepository applicantRepository;

	public void updateVoucherStatus(Voucher voucher) {
		voucher.setStatus(TokenStatus.USED);
		voucherRepository.save(voucher);
	}

	public Voucher getVoucher(Long serialNumber) {
		return voucherRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Voucher")));
	}

	public Applicant loadApplicant(Long id) {
		return applicantRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Applicant")));
	}

	public Program getProgram(Long id) {
		return programRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Program")));
	}

	public void validApplicantResult(Long userId, Long resultId) {
		if (!applicantRepository.existsByUserIdAndResultsId(userId, resultId)) {
			throw new UnauthorizedAccessException(
					String.format(ExceptionConstants.UNAUTHORIZED_ACCESS));
		}
	}

}
