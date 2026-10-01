package com.shinkwang.devjob.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceLoggingAspect {

    private static final long SLOW_THRESHOLD_MS = 500;

    @Around("execution(* com.shinkwang.devjob..service..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startNanos = System.nanoTime();
        try {
            Object result = joinPoint.proceed();
            long elapsed = (System.nanoTime() - startNanos) / 1_000_000;

            if (elapsed > SLOW_THRESHOLD_MS) {
                log.warn("[SLOW] {} - {}ms", joinPoint.getSignature().toShortString(), elapsed);
            } else {
                log.debug("[OK] {} - {}ms", joinPoint.getSignature().toShortString(), elapsed);
            }
            return result;
        } catch (Exception e) {
            log.error("[FAIL] {}", joinPoint.getSignature().toShortString(), e);
            throw e;
        }
    }
}
