package dev.aries.sagehub.service.voucherservice;

import dev.aries.sagehub.dto.request.AddVoucherRequest;
import dev.aries.sagehub.dto.request.VoucherRequest;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.dto.response.VoucherResponse;
import dev.aries.sagehub.dto.search.GetVouchersPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoucherService {
	GenericResponse addVouchers(AddVoucherRequest request);

	Page<VoucherResponse> getVouchers(GetVouchersPage request, Pageable pageable);

	void verifyVoucher(VoucherRequest request);
}
