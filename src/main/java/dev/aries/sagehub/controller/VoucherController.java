package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.VoucherRequest;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.dto.response.VoucherResponse;
import dev.aries.sagehub.dto.search.GetVouchersPage;
import dev.aries.sagehub.service.voucherservice.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/vouchers")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SCOPE_SUPER_ADMIN', 'SCOPE_ADMIN')")
public class VoucherController {
	private final VoucherService voucherService;

	@PostMapping
	public ResponseEntity<GenericResponse> addVouchers(@RequestBody @Valid VoucherRequest request) {
		return ResponseEntity.ok(this.voucherService.addVouchers(request));
	}

	@GetMapping
	public ResponseEntity<Page<VoucherResponse>> getVouchers(
			GetVouchersPage request, @PageableDefault Pageable pageable) {
		return ResponseEntity.ok(this.voucherService.getVouchers(request, pageable));
	}
}
