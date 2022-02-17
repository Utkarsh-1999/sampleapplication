package com.example.sampleapplication.ratelimiter.methods.leakybucket;


import com.example.sampleapplication.exception.ratelimiter.RateLimitExceededException;
import com.example.sampleapplication.ratelimiter.methods.leakybucket.bucket.Bucket;
import com.example.sampleapplication.ratelimiter.methods.leakybucket.bucket.BucketRepo;
import com.example.sampleapplication.ratelimiter.methods.leakybucket.bucket.BucketWatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Objects;


@Component
public class LeakyBucket {

    @Autowired
    BucketRepo repo;

    HashMap<String, BucketWatcher> bucketWatcherHashMap;

    public LeakyBucket() {
        this.bucketWatcherHashMap = new HashMap<>();

    }

    public void limitRequest(String id, long millisecondsPerRequest, long leakyBucketCapacity) throws InterruptedException {



        Bucket bucket=repo.getBucketById(id);

        if(bucket.getRequestQueue().size()>=leakyBucketCapacity){
            throw new RateLimitExceededException();
        }

        BucketWatcher watcher;

        if(bucketWatcherHashMap.get(id)==null){
            watcher=new BucketWatcher(id,millisecondsPerRequest,repo);
            bucketWatcherHashMap.put(id,watcher);
        }
        else{
            watcher=bucketWatcherHashMap.get(id);
        }

        if(bucket.getRequestQueue().isEmpty())
            bucket.setQueueFrontTime(LocalTime.now());

        String requestId=id+bucket.getRequestQueue().size();



        bucket.getRequestQueue().add(requestId);
        repo.updateBucketById(id,bucket);




        while(!Objects.equals(watcher.pullRequest(), requestId));



    }


}
