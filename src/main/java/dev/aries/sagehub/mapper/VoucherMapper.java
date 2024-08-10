package dev.aries.sagehub.mapper;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.response.VoucherResponse;
import dev.aries.sagehub.model.Voucher;

public final class VoucherMapper {
	public static VoucherResponse toResponse(Voucher voucher) {
		return new VoucherResponse(
				voucher.getId(),
				voucher.getSerialNumber(),
				voucher.getPin(),
				voucher.getAcademicYear().getYear(),
				voucher.getStatus().toString()
		);
	}

	private VoucherMapper() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
