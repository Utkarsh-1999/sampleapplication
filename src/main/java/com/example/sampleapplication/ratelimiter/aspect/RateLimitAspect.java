package com.example.sampleapplication.ratelimiter.aspect;

import com.example.sampleapplication.ratelimiter.annotation.RateLimit;
import com.example.sampleapplication.ratelimiter.keygenerator.EmailBasedKeyGenerator;
import com.example.sampleapplication.ratelimiter.keygenerator.IpBasedKeyGenerator;
import com.example.sampleapplication.ratelimiter.keygenerator.RateLimiterKeyGenerator;
import com.example.sampleapplication.ratelimiter.keygenerator.RequestKey;
import com.example.sampleapplication.ratelimiter.mechanism.RateLimiter;
import com.example.sampleapplication.ratelimiter.requestcontext.RequestContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
public class RateLimitAspect {

    Logger logger=LoggerFactory.getLogger(RateLimitAspect.class);

    @Value("${rate-limiter.enable:true}")
    boolean enable;


    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    RequestContext requestContext;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    RateLimiter rateLimiter;



    @Before("@annotation(rateLimit)")
    public void LimitRequests(JoinPoint joinPoint,RateLimit rateLimit) throws InterruptedException {


        RateLimiterKeyGenerator rateLimiterKeyGenerator=applicationContext.getBean(rateLimit.keyGenerator(),RateLimiterKeyGenerator.class);

        requestContext.setPrefix(((MethodSignature)joinPoint.getSignature()).getMethod().getName());


        if(rateLimiterKeyGenerator instanceof IpBasedKeyGenerator) {
            requestContext.setSuffix(httpServletRequest);
        }

        if(rateLimiterKeyGenerator instanceof EmailBasedKeyGenerator){
            Object [] paramAnnotations=((MethodSignature)joinPoint.getSignature()).getMethod().getParameterAnnotations();
            Object [] parameters=joinPoint.getArgs();


            for(int i=0;i<joinPoint.getArgs().length;i++){
                if(paramAnnotations[i] instanceof RequestBody){
                    requestContext.setSuffix(parameters[i]);
                }
            }
        }



        RequestKey requestKey=rateLimiterKeyGenerator.generateKey();


        if(enable){

            rateLimiter.tryAcceptRequest(requestKey);

        }

    }

}
