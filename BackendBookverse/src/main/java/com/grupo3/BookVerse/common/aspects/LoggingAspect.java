package com.grupo3.BookVerse.common.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
// Logs method execution across controllers and services,
// including method entry, exit, execution time, and exceptions using Spring AOP.

    @Around(
            "execution(* com.grupo3.BookVerse.features..controller..*(..)) || " +
                    "execution(* com.grupo3.BookVerse.features..service..*(..))"
    )
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("Entering {}.{}", className, methodName);

        try {
            Object result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;
            log.info("Exiting {}.{} - executed in {} ms", className, methodName, executionTime);

            return result;

        } catch (Throwable throwable) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error(
                    "Exception in {}.{} after {} ms - message: {}",
                    className,
                    methodName,
                    executionTime,
                    throwable.getMessage()
            );
            throw throwable;
        }
    }
}