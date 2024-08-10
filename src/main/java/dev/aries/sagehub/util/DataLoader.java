package dev.aries.sagehub.util;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.enums.TokenStatus;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.model.Voucher;
import dev.aries.sagehub.repository.ApplicationRepository;
import dev.aries.sagehub.repository.ProgramRepository;
import dev.aries.sagehub.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicantUtil {
	private final VoucherRepository voucherRepository;
	private final ProgramRepository programRepository;
	private final ApplicationRepository applicationRepository;

	public void updateVoucherStatus(Voucher voucher) {
		voucher.setStatus(TokenStatus.USED);
		voucherRepository.save(voucher);
	}

	public Program getProgram(Long id) {
		return programRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Program")));
	}

}
