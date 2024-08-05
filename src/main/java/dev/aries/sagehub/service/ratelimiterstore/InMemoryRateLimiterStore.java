package dev.aries.sagehub.service.ratelimiterstore;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

import org.springframework.stereotype.Component;
/**
 * Implementation of the {@code RateLimiterStore} interface.
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.ratelimiterstore.RateLimiterStore
 */
@Component
public class InMemoryRateLimiterStore implements RateLimiterStore {
	private final ConcurrentHashMap<UUID, RateLimitEntry> rateLimits = new ConcurrentHashMap<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean tryAcquire(UUID clientId, int maxRequests, long timeIntervalInMinutes) {
		RateLimitEntry entry = rateLimits.computeIfAbsent(clientId, (key) ->
				new RateLimitEntry(maxRequests, LocalDateTime.now()));
		LocalDateTime currentTime = LocalDateTime.now();
		if (entry.getLastRequestTime().isBefore(currentTime.minusMinutes(timeIntervalInMinutes))) {
			entry.reset(currentTime);
			return true;
		}
		if (entry.getRequestCount() < maxRequests) {
			entry.incrementRequestCount();
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetAll() {
		rateLimits.clear();
	}

	/**
	 * The {@code RateLimitEntry} class represents an entry for rate limiting.
	 * It keeps track of the maximum number of requests allowed, the current request count,
	 * and the time of the last request.
	 * <p>
	 * This class is used internally by the rate limiter to manage and enforce rate limits.
	 * </p>
	 * @author Jacobs Agyei
	 */
	@Data
	private static class RateLimitEntry {
		private final int maxRequests;
		private int requestCount;
		private LocalDateTime lastRequestTime;

		RateLimitEntry(int maxRequests, LocalDateTime lastRequestTime) {
			this.maxRequests = maxRequests;
			requestCount = 0;
			this.lastRequestTime = lastRequestTime;
		}

		void reset(LocalDateTime lastRequestTime) {
			requestCount = 0;
			this.lastRequestTime = lastRequestTime;
		}

		void incrementRequestCount() {
			requestCount++;
		}
	}
}
