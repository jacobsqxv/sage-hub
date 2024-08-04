package dev.aries.sagehub.config;

import java.security.Principal;
import java.util.UUID;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.security.RateLimiterService;
import dev.aries.sagehub.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RequestIdAspect {
	private final RateLimiterService rateLimiterService;
	private final UUID anonymous = UUID.fromString("00000000-0000-0000-0000-000000000000");

	/**
	 * Aspect method that wraps around the execution of any method in the controller package.
	 * It generates a unique request ID for each request, checks the rate limit for the client,
	 * and ensures the request ID is removed from the MDC (Mapped Diagnostic Context) after the method execution.
	 * @param joinPoint the join point representing the method being executed
	 * @return the result of the method execution
	 * @throws Throwable if any error occurs during the method execution
	 */
	@Around("execution(* dev.aries.sagehub.controller.*.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MDC.put("requestId", UUID.randomUUID().toString());
		try {
			UUID clientId = getClientId();
			if (!clientId.equals(anonymous)) {
				rateLimiterService.checkRateLimit(clientId);
			}
			return joinPoint.proceed();
		}
		finally {
			MDC.remove("requestId");
		}
	}

	public UUID getClientId() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attr == null) {
			return null;
		}
		Principal principal = attr.getRequest().getUserPrincipal();
		if (principal == null) {
			log.info("Principal is null, handling as anonymous request");
			return anonymous;
		}
		return switch (principal) {
			case UserDetailsImpl userDetails -> userDetails.getClientId();
			case JwtAuthenticationToken jwt -> UUID.fromString(jwt.getToken().getClaim("clientId"));
			default -> throw new IllegalArgumentException(ExceptionConstants.AUTHENTICATION_FAILED);
		};
	}
}
