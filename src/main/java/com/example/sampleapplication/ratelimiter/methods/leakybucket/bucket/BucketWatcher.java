package com.example.sampleapplication.ratelimiter.methods.leakybucket.bucket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

public class BucketWatcher {


    BucketRepo repo;

    String id;
    long millisecondsPerRequest;


    public BucketWatcher(String id, long millisecondsPerRequest, BucketRepo repo) {
        this.id=id;
        this.millisecondsPerRequest=millisecondsPerRequest;
        this.repo=repo;
    }

    public String pullRequest() throws InterruptedException {



        while(Duration.between(repo.getBucketById(id).getQueueFrontTime(), LocalTime.now() ).toMillis() < millisecondsPerRequest);


        Bucket bucket=repo.getBucketById(id);
        String requestId=bucket.getRequestQueue().poll();
        bucket.setQueueFrontTime(LocalTime.now());

        repo.updateBucketById(id,bucket);
        return requestId;


    }
}
