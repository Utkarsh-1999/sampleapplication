package com.example.sampleapplication.ratelimiter.bin.methods.leakybucket.bucket;

import java.time.Duration;
import java.time.LocalTime;

public class BucketWatcher extends Thread {


    BucketRepo repo;

    String id;
    long millisecondsPerRequest;


    public BucketWatcher(String id, long millisecondsPerRequest, BucketRepo repo) {
        this.id=id;
        this.millisecondsPerRequest=millisecondsPerRequest;
        this.repo=repo;
    }

    public String getRequestId() {



        while(Duration.between(repo.getBucketById(id).getQueueFrontEntryTime(), LocalTime.now() ).toMillis() < millisecondsPerRequest);

        Bucket bucket=repo.getBucketById(id);
        String requestId=bucket.getRequestQueue().poll();
        bucket.setQueueFrontEntryTime(LocalTime.now());
        repo.updateBucketById(id,bucket);

        return requestId;

    }




}
