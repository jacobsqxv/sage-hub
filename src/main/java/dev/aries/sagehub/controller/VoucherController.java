package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.AddVoucherRequest;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.dto.response.VoucherResponse;
import dev.aries.sagehub.dto.search.GetVouchersPage;
import dev.aries.sagehub.service.voucherservice.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
@Tag(name = "Voucher", description = "Voucher management")
public class VoucherController {
	private final VoucherService voucherService;

	@PostMapping
	@Operation(summary = "Add vouchers",
			description = "Add a number of vouchers to the system",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "201", description = "Vouchers added successfully",
			content = {@Content(schema = @Schema(implementation = GenericResponse.class))})
	public ResponseEntity<GenericResponse> addVouchers(@RequestBody @Valid AddVoucherRequest request) {
		return new ResponseEntity<>(voucherService.addVouchers(request), HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "Get vouchers",
			description = "Get vouchers as page, supports search and filter",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Vouchers retrieved successfully",
			content = {@Content(schema = @Schema(implementation = VoucherResponse.class))})
	public ResponseEntity<Page<VoucherResponse>> getVouchers(
			GetVouchersPage request, @PageableDefault Pageable pageable) {
		return ResponseEntity.ok(voucherService.getVouchers(request, pageable));
	}

}
