package com.example.sampleapplication.ratelimiter.aspect;

import com.example.sampleapplication.ratelimiter.annotation.RateLimit;
import com.example.sampleapplication.ratelimiter.keygenerator.EmailBasedKeyGenerator;
import com.example.sampleapplication.ratelimiter.keygenerator.IpBasedKeyGenerator;
import com.example.sampleapplication.ratelimiter.keygenerator.RateLimiterKeyGenerator;
import com.example.sampleapplication.ratelimiter.keygenerator.RequestKey;
import com.example.sampleapplication.ratelimiter.mechanism.RateLimiter;
import com.example.sampleapplication.ratelimiter.requestcontext.RequestContext;
import com.example.sampleapplication.ratelimiter.bin.methods.fixedwindowcounter.FixedWindowCounter;
import com.example.sampleapplication.ratelimiter.bin.methods.leakybucket.LeakyBucket;
import com.example.sampleapplication.ratelimiter.bin.methods.slidingwindowcounter.SlidingWindowCounter;
import com.example.sampleapplication.ratelimiter.bin.methods.tokenbucket.TokenBucket;
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


    @Value("${rate-limiter.method:Sliding-Window-Counter}")
    String method;

    @Value("${rate-limiter.fixedWindowResetDurationInMilliseconds:10000}")
    long fixedWindowResetDurationInMilliseconds;
    @Value("${rate-limiter.fixedWindowRequestThreshold:5}")
    long fixedWindowRequestThreshold;


    @Value("${rate-limiter.tokenRatePerSecond:0.1}")
    double tokenRatePerSecond;
    @Value("${rate-limiter.tokenBucketCapacity:5}")
    long tokenBucketCapacity;


    @Value("${rate-limiter.millisecondsPerRequest:10000}")
    long millisecondsPerRequest;
    @Value("${rate-limiter.leakyBucketCapacity:5}")
    long leakyBucketCapacity;


    @Value("${rate-limiter.windowDurationInMilliseconds:1}")
    long windowDurationInMilliseconds;
    @Value("${rate-limiter.slidingWindowSize:5}")
    long slidingWindowSize;


    @Autowired
    SlidingWindowCounter slidingWindowCounter;

    @Autowired
    FixedWindowCounter fixedWindowCounter;

    @Autowired
    TokenBucket tokenBucket;

    @Autowired
    LeakyBucket leakyBucket;


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

//            switch (method) {
//                case "Fixed-Window-Counter" -> fixedWindowCounter.limitRequest(key,fixedWindowResetDurationInMilliseconds,fixedWindowRequestThreshold);
//                case "Token-Bucket" -> tokenBucket.limitRequest(key,tokenRatePerSecond,tokenBucketCapacity);
//                case "Leaky-Bucket" -> leakyBucket.limitRequest(key, millisecondsPerRequest,leakyBucketCapacity);
//                default -> slidingWindowCounter.limitRequest(key, windowDurationInMilliseconds, slidingWindowSize);
//            }

        }

    }

}
