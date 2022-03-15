package com.example.sampleapplication.ratelimiter.bin.methods.tokenbucket.requesttoken;

import java.time.Duration;
import java.time.LocalTime;

import static java.lang.Math.min;


public class TokenGenerationService extends Thread{

    String id;
    double tokenRatePerSecond;
    long tokenBucketCapacity;
    RequestTokenBucketRepo repo;
    Boolean activeFlag;

    public TokenGenerationService(String id, double tokenRatePerSecond, long tokenBucketCapacity, RequestTokenBucketRepo repo){
        this.id=id;
        this.tokenRatePerSecond=tokenRatePerSecond;
        this.tokenBucketCapacity=tokenBucketCapacity;
        this.repo=repo;
        this.activeFlag=true;
    }

    public void run(){


        while(activeFlag)
        {
            if(Duration.between(repo.getRequestTokenBucketById(id).getLastGeneratedTokenTime() , LocalTime.now()).toSeconds()>=1){
                RequestTokenBucket tokenBucket=repo.getRequestTokenBucketById(id);

                tokenBucket.setTokenCount(min(tokenBucketCapacity , tokenBucket.getTokenCount()+tokenRatePerSecond));
                tokenBucket.setLastGeneratedTokenTime(LocalTime.now());

                repo.updateRequestTokenBucketById(id,tokenBucket);
            }

        }

    }

    public void stopService(){
        this.activeFlag=false;
    }
}
