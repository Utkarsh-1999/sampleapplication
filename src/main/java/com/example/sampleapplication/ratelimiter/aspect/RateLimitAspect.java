package com.example.sampleapplication.ratelimiter.aspect;

import com.example.sampleapplication.ratelimiter.annotation.RateLimit;
import com.example.sampleapplication.ratelimiter.methods.fixedwindowcounter.FixedWindowCounter;
import com.example.sampleapplication.ratelimiter.methods.leakybucket.LeakyBucket;
import com.example.sampleapplication.ratelimiter.methods.slidingwindowcounter.SlidingWindowCounter;
import com.example.sampleapplication.ratelimiter.methods.tokenbucket.TokenBucket;
import com.example.sampleapplication.ratelimiter.parser.SpelParser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


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


    @Value("${rate-limiter.millisecondsPerWindowFrame:10000}")
    long millisecondsPerWindowFrame;
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
    SpelParser spelParser;




    @Before("@annotation(rateLimit)")
    public void LimitRequests(JoinPoint joinPoint,RateLimit rateLimit) throws InterruptedException {

        String id=spelParser.parseExpression(rateLimit.expression(), joinPoint);

        if(enable){



            switch (method) {
                case "Fixed-Window-Counter" -> fixedWindowCounter.limitRequest(id,fixedWindowResetDurationInMilliseconds,fixedWindowRequestThreshold);
                case "Token-Bucket" -> tokenBucket.limitRequest(id,tokenRatePerSecond,tokenBucketCapacity);
                case "Leaky-Bucket" -> leakyBucket.limitRequest(id, millisecondsPerRequest,leakyBucketCapacity);
                default -> slidingWindowCounter.limitRequest(id,millisecondsPerWindowFrame, slidingWindowSize);
            }

        }








    }

}
