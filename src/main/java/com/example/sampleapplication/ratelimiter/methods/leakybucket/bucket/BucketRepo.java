package com.example.sampleapplication.ratelimiter.methods.leakybucket.bucket;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class BucketRepo {

    @Cacheable(value="Leaky-Bucket",key="#id")
    public Bucket getBucketById(String id){
        return new Bucket();
    }

    @CachePut(value="Leaky-Bucket",key="#id")
    public Bucket updateBucketById(String id,Bucket bucket)
    {
        return bucket;
    }



}
