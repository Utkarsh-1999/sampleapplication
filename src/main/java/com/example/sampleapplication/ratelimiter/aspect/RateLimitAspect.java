package com.example.sampleapplication.ratelimiter.aspect;

import com.example.sampleapplication.db.entity.data.Data;
import com.example.sampleapplication.ratelimiter.annotation.RateLimit;
import com.example.sampleapplication.ratelimiter.keygenerator.IdBasedKeyGenerator;
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

import java.lang.annotation.Annotation;


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
    RateLimiter rateLimiter;



    @Before("@annotation(rateLimit)")
    public void LimitRequests(JoinPoint joinPoint,RateLimit rateLimit) throws Exception {



        RateLimiterKeyGenerator rateLimiterKeyGenerator=applicationContext.getBean(rateLimit.keyGenerator(),RateLimiterKeyGenerator.class);

        requestContext.setRequestPath(((MethodSignature)joinPoint.getSignature()).getMethod().getName());


        if(rateLimiterKeyGenerator instanceof IdBasedKeyGenerator){
            Annotation[][] paramAnnotations=((MethodSignature)joinPoint.getSignature()).getMethod().getParameterAnnotations();
            Object [] parameters=joinPoint.getArgs();

            boolean isRequestBodyPresent=false;

            for(int i=0;i<joinPoint.getArgs().length;i++){
                if((paramAnnotations[i][0] instanceof RequestBody) && (parameters[i] instanceof  Data)){
                    requestContext.setId(((Data)parameters[i]).getEmail());
                    isRequestBodyPresent=true;
                    break;
                }
            }

            if(!isRequestBodyPresent){

                throw new Exception();
            }
        }



        RequestKey requestKey=rateLimiterKeyGenerator.generateKey();


        if(enable){

            rateLimiter.tryAcceptRequest(requestKey);

        }

    }

}
