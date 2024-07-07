package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.VoucherResponse;
import dev.aries.sagehub.model.Voucher;

import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {
	public VoucherResponse toVoucherResponse(Voucher voucher) {
		return new VoucherResponse(
				voucher.getSerialNumber(),
				voucher.getPin(),
				voucher.getStatus().toString()
		);
	}
}