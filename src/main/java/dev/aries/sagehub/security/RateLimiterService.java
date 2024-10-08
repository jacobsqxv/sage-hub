package dev.aries.sagehub.security;

import java.util.UUID;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.exception.RateLimitException;
import dev.aries.sagehub.service.ratelimiterstore.InMemoryRateLimiterStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimiterService {

	private final InMemoryRateLimiterStore rateLimiterStore;

	@Value("${application.rate-limiter.max-requests}")
	private int maxRequests;

	@Value("${application.rate-limiter.time-interval}")
	private long timeIntervalInMinutes;

	public boolean tryAcquire(UUID clientId) {
		return rateLimiterStore.tryAcquire(clientId, maxRequests, timeIntervalInMinutes);
	}

	public void checkRateLimit(UUID clientId) {
		if (!tryAcquire(clientId)) {
			throw new RateLimitException(ExceptionConstants.RATE_LIMIT_EXCEEDED);
		}
	}

	@Scheduled(fixedRateString = "${application.rate-limiter.reset-interval}")
	public void resetCounts() {
		rateLimiterStore.resetAll();
	}
}
