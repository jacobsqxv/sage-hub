package dev.aries.sagehub.dto.response;

public record VoucherResponse(
		Long serialNumber,
		String pin,
		String status
) {
}
