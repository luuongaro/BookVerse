package com.grupo3.BookVerse.common.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect /// -->> automatic logic.
@Component
/// This class logs controller method executions before they run,
///without modifying the controller code.

public class LoggingAspect {

    /// Logs the name of each controller method before it is executed.

    @Before("execution(* com.grupo3.BookVerse.features..*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        System.out.println("Executing Method: " + joinPoint.getSignature().toShortString());
    }
}
