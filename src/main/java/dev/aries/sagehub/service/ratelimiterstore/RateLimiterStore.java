package dev.aries.sagehub.service.ratelimiterstore;

import java.util.UUID;

/**
 * The {@code RateLimiterStore} interface provides methods for rate limiting.
 * It includes functionality for attempting to acquire a permit for a client and resetting all rate limits.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to rate limiting.
 * </p>
 * @author Jacobs Agyei
 */
public interface RateLimiterStore {

	/**
	 * Attempts to acquire a permit for the specified client.
	 * <p>
	 * This method takes a {@code UUID} representing the client ID, the maximum number of requests allowed,
	 * and the time interval in minutes. It returns {@code true} if the request is allowed, or {@code false}
	 * if the rate limit is exceeded.
	 * </p>
	 * @param clientId the {@code UUID} of the client.
	 * @param maxRequests the maximum number of requests allowed.
	 * @param timeIntervalInMinutes the time interval in minutes for the rate limit.
	 * @return {@code true} if the request is allowed, or {@code false} if the rate limit is exceeded.
	 * @throws IllegalArgumentException if any of the parameters are null or invalid.
	 */
	boolean tryAcquire(UUID clientId, int maxRequests, long timeIntervalInMinutes);

	/**
	 * Resets all rate limits.
	 * <p>
	 * This method resets the rate limits for all clients.
	 * </p>
	 */
	void resetAll();
}
