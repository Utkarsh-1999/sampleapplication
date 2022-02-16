package com.example.sampleapplication.ratelimiter.methods.leakybucket.bucket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

public class BucketWatcher {


    BucketRepo repo;

    String id;
    long millisecondsPerRequest;
    private LocalTime lastReqTime;

    public LocalTime getLastReqTime() {
        return lastReqTime;
    }

    public void setLastReqTime(LocalTime lastReqTime) {
        this.lastReqTime = lastReqTime;
    }

    public BucketWatcher(String id, long millisecondsPerRequest, BucketRepo repo) {
        this.id=id;
        this.millisecondsPerRequest=millisecondsPerRequest;
        this.repo=repo;
    }

    public String pullRequest() throws InterruptedException {



        while(Duration.between(lastReqTime, LocalTime.now() ).toMillis() < millisecondsPerRequest);

        this.lastReqTime=LocalTime.now();


        Bucket bucket=repo.getBucketById(id);
        String requestId=bucket.getRequestQueue().poll();

        repo.updateBucketById(id,bucket);
        return requestId;


    }
}
