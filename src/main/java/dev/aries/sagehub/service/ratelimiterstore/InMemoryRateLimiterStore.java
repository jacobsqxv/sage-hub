package dev.aries.sagehub.service.ratelimiterstore;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

import org.springframework.stereotype.Component;

@Component
public class InMemoryRateLimiterStore implements RateLimiterStore {
	private final ConcurrentHashMap<UUID, RateLimitEntry> rateLimits = new ConcurrentHashMap<>();

	@Override
	public boolean tryAcquire(UUID clientId, int maxRequests, long timeIntervalInMinutes) {
		RateLimitEntry entry = this.rateLimits.computeIfAbsent(clientId, (key) ->
				new RateLimitEntry(maxRequests, LocalDateTime.now()));
		LocalDateTime currentTime = LocalDateTime.now();
		if (entry.getLastRequestTime().isBefore(currentTime.minusMinutes(timeIntervalInMinutes))) {
			entry.reset(maxRequests, currentTime);
			return true;
		}
		if (entry.getRequestCount() < maxRequests) {
			entry.incrementRequestCount();
			return true;
		}
		return false;
	}

	@Override
	public void resetAll() {
		this.rateLimits.clear();
	}

	@Data
	private static class RateLimitEntry {
		private final int maxRequests;
		private int requestCount;
		private LocalDateTime lastRequestTime;

		RateLimitEntry(int maxRequests, LocalDateTime lastRequestTime) {
			this.maxRequests = maxRequests;
			this.requestCount = 0;
			this.lastRequestTime = lastRequestTime;
		}

		void reset(int maxRequests, LocalDateTime lastRequestTime) {
			this.requestCount = 0;
			this.lastRequestTime = lastRequestTime;
		}

		void incrementRequestCount() {
			this.requestCount++;
		}
	}
}
