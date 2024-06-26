package dev.aries.sagehub.config;

import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;

import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestIdAspect {
	@Around("execution(* dev.aries.sagehub.controller.*.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MDC.put("requestId", UUID.randomUUID().toString());
		try {
			return joinPoint.proceed();
		} finally {
			MDC.remove("requestId");
		}
	}
}
