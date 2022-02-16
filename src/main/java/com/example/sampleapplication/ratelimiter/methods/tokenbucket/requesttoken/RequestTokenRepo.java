package com.example.sampleapplication.ratelimiter.methods.tokenbucket.requesttoken;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class RequestTokenRepo {

    @Cacheable(value="TokenBucket",key="#id")
    public RequestTokenModel getRequestTokenById(String id, long tokenBucketCapacity){
        return new RequestTokenModel(tokenBucketCapacity, LocalTime.now());
    }

    @CachePut(value="TokenBucket",key="#id")
    public RequestTokenModel updateRequestTokenById(String id,double tokenCount){
        return new RequestTokenModel(tokenCount,LocalTime.now());
    }

}
