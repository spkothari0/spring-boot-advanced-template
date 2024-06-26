package com.shreyas.spring_boot_advanced_template.aop.Logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Aspect
@Component
public class LogAspect {

    @Around(value = "com.shreyas.spring_boot_advanced_template.aop.Logging.AppPointcuts.mainPointcuts()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        final Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        if(logger.isDebugEnabled()){
            return joinPoint.proceed();
        }

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String args = Stream.of(joinPoint.getArgs()).toList().toString();

        Object result = joinPoint.proceed();

        LoggerMessage message = new LoggerMessage(className, methodName, args);
        logger.info("Logging: {}", message);
        return result;
    }
}
