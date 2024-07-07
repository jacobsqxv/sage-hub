package dev.aries.sagehub.service.voucherservice;

import java.util.ArrayList;
import java.util.List;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.constant.ResponseMessage;
import dev.aries.sagehub.dto.request.VoucherRequest;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.dto.response.VoucherResponse;
import dev.aries.sagehub.dto.search.GetVouchersPage;
import dev.aries.sagehub.enums.VoucherStatus;
import dev.aries.sagehub.mapper.VoucherMapper;
import dev.aries.sagehub.model.AcademicYear;
import dev.aries.sagehub.model.Voucher;
import dev.aries.sagehub.repository.AcademicYearRepository;
import dev.aries.sagehub.repository.VoucherRepository;
import dev.aries.sagehub.util.Generators;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
	private static final int LIMIT = 1000;
	private final VoucherRepository voucherRepository;
	private final AcademicYearRepository academicYearRepository;
	private final Generators generators;
	private final VoucherMapper voucherMapper;
	private static final Integer STATUS_OK = HttpStatus.OK.value();

	@Override
	public GenericResponse addVouchers(VoucherRequest request) {
		checkVouchers(request.year());
		AcademicYear year = getAcademicYear(request.year());
		List<Voucher> vouchers = new ArrayList<>();
		for (int i = 0; i < request.quantity(); i++) {
			Long serialNumber = this.generators.generateUniqueId(true);
			String pin = this.generators.generateToken(10);
			vouchers.add(Voucher.builder()
							.serialNumber(serialNumber)
							.pin(pin)
							.academicYear(year)
							.status(VoucherStatus.ACTIVE)
					.build());
		}
		this.saveVouchers(vouchers);
		return new GenericResponse(STATUS_OK, String.format(ResponseMessage.ADD_SUCCESS, "Vouchers"));
	}

	@Override
	public Page<VoucherResponse> getVouchers(GetVouchersPage request, Pageable pageable) {
		return this.voucherRepository.findAll(request.year(), request.status(), pageable)
				.map(this.voucherMapper::toVoucherResponse);
	}

	private void saveVouchers(List<Voucher> vouchers) {
		if (vouchers.size() > 1) {
			this.voucherRepository.saveAll(vouchers);
		}
		else {
			this.voucherRepository.save(vouchers.getFirst());
		}
	}

	private void checkVouchers(Integer year) {
		List<Voucher> vouchers = this.voucherRepository.findAllByAcademicYear(getAcademicYear(year));
		if (!vouchers.isEmpty() && vouchers.size() >= LIMIT) {
			throw new IllegalArgumentException(ExceptionConstants.VOUCHERS_LIMIT);
		}
	}

	private AcademicYear getAcademicYear(Integer year) {
		return this.academicYearRepository.findByYear(year)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Academic Year")));
	}
}
