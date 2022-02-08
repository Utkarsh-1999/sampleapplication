package com.example.sampleapplication.aspect.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    Logger logger= LoggerFactory.getLogger(LoggingAspect.class);
    @Before("execution(* com.example.sampleapplication.service.*.*.*(..))")
    public void entryLoggin(JoinPoint joinPoint) throws Throwable{
        logger.info("Entering Method: "+joinPoint.getSignature());
    }

    @After("execution(* com.example.sampleapplication.service.*.*.*(..))")
    public void exitLoggin(JoinPoint joinPoint) throws Throwable{
        logger.info("Exiting Method: "+joinPoint.getSignature());
    }

}
