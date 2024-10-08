package dev.aries.sagehub.service.voucherservice;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.constant.ResponseMessage;
import dev.aries.sagehub.dto.request.AddVoucherRequest;
import dev.aries.sagehub.dto.request.VoucherRequest;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.dto.response.VoucherResponse;
import dev.aries.sagehub.dto.search.GetVouchersPage;
import dev.aries.sagehub.enums.TokenStatus;
import dev.aries.sagehub.mapper.VoucherMapper;
import dev.aries.sagehub.model.AcademicYear;
import dev.aries.sagehub.model.Voucher;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.repository.AcademicYearRepository;
import dev.aries.sagehub.repository.VoucherRepository;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.DataLoader;
import dev.aries.sagehub.util.Generators;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
/**
 * Implementation of the {@code VoucherService} interface.
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.voucherservice.VoucherService
 */
@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
	private static final Integer STATUS_OK = HttpStatus.OK.value();
	private static final String VOUCHER = "Voucher";
	private static final int LIMIT = 1000;
	private final AcademicYearRepository academicYearRepository;
	private final VoucherRepository voucherRepository;
	private final Generators generators;
	private final DataLoader dataLoader;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GenericResponse addVouchers(AddVoucherRequest request) {
		checkVouchers(request.year());
		AcademicYear year = getAcademicYear(request.year());
		List<Voucher> vouchers = new ArrayList<>();
		for (int i = 0; i < request.quantity(); i++) {
			Long serialNumber = generators.generateUniqueId(true);
			Password pin = Generators.generatePassword(8);
			LocalDate expiresAt = year.getStartDate().plusMonths(2);
			vouchers.add(Voucher.builder()
					.serialNumber(serialNumber)
					.pin(pin.value())
					.academicYear(year)
					.status(TokenStatus.ACTIVE)
					.expiresAt(expiresAt.atTime(LocalTime.MAX))
					.build());
		}
		saveVouchers(vouchers);
		return new GenericResponse(STATUS_OK, String.format(ResponseMessage.ADD_SUCCESS, "Vouchers"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<VoucherResponse> getVouchers(GetVouchersPage request, Pageable pageable) {
		if (request.status() != null) {
			Checks.checkIfEnumExists(TokenStatus.class, request.status());
		}
		return voucherRepository.findAll(request.year(), request.status(), pageable)
				.map(VoucherMapper::toResponse);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void verifyVoucher(VoucherRequest request) {
		Voucher voucher = dataLoader.loadVoucher(request.serialNumber());
		if (!voucher.getPin().equals(request.pin())) {
			throw new IllegalArgumentException(ExceptionConstants.INVALID_TOKEN);
		}
		checkValidity(voucher);

	}

	private void saveVouchers(List<Voucher> vouchers) {
		if (vouchers.size() > 1) {
			voucherRepository.saveAll(vouchers);
		}
		else {
			voucherRepository.save(vouchers.getFirst());
		}
	}

	private void checkVouchers(Integer year) {
		List<Voucher> vouchers = voucherRepository.findAllByAcademicYear(getAcademicYear(year));
		if (!vouchers.isEmpty() && vouchers.size() >= LIMIT) {
			throw new IllegalArgumentException(ExceptionConstants.VOUCHERS_LIMIT);
		}
	}

	private AcademicYear getAcademicYear(Integer year) {
		return academicYearRepository.findByYear(year)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Academic Year")));
	}

	private void checkValidity(Voucher voucher) {
		TokenStatus status = voucher.getStatus();
		if (!status.equals(TokenStatus.ACTIVE) && !status.equals(TokenStatus.USED)) {
			throw new IllegalArgumentException(
					String.format(ExceptionConstants.INVALID_TOKEN, VOUCHER));
		}
	}

}
