package com.example.sampleapplication.timetracker.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class TimeTrackerAspect {

    Logger logger= LoggerFactory.getLogger(TimeTrackerAspect.class);

    @Around("@annotation(com.example.sampleapplication.timetracker.annotation.TimeTracker)")
    public Object tracker(ProceedingJoinPoint joinPoint) throws Throwable{
        long starttime=System.currentTimeMillis();
        Object result=joinPoint.proceed();
        long endtime=System.currentTimeMillis();

        logger.info("Method : "+joinPoint.getSignature()+" took "+(endtime-starttime)+" ms to execute");

        return result;
    }

}
