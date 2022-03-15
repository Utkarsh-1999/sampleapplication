package com.example.sampleapplication.ratelimiter.bin.methods.tokenbucket.requesttoken;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class RequestTokenBucketRepo {

    @Cacheable(value="TokenBucket",key="#id")
    public RequestTokenBucket getRequestTokenBucketById(String id){
        return new RequestTokenBucket();
    }

    @CachePut(value="TokenBucket",key="#id")
    public RequestTokenBucket updateRequestTokenBucketById(String id, RequestTokenBucket requestTokenBucket){
        return requestTokenBucket;
    }

}
