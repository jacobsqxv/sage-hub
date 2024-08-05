package dev.aries.sagehub.util;

import dev.aries.sagehub.repository.TokenRepository;
import dev.aries.sagehub.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CleanUp {
	private final TokenRepository tokenRepository;
	private final VoucherRepository voucherRepository;

	@Scheduled(cron = "${application.clean-up.cron}")
	@Transactional
	public void cleanUp() {
		log.info("Starting clean up...");
		cleanUpExpiredTokens();
		cleanUpExpiredVouchers();
	}

	private void cleanUpExpiredTokens() {
		tokenRepository.deleteAllExpiredTokens();
		log.info("Cleaning up expired tokens...");
	}

	private void cleanUpExpiredVouchers() {
		voucherRepository.deleteAllExpiredVouchers();
		log.info("Cleaning up expired vouchers...");
	}
}
