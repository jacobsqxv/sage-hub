package dev.aries.sagehub.service.ratelimiterstore;

import java.util.UUID;

public interface RateLimiterStore {
	boolean tryAcquire(UUID clientId, int maxRequests, long timeIntervalInMinutes);

	void resetAll();
}
