package dev.aries.sagehub.dto.response;

public record VoucherResponse(
		Long id,
		Long serialNumber,
		String pin,
		String status
) {
}
