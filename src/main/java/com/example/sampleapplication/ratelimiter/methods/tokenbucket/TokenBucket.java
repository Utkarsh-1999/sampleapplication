package com.example.sampleapplication.ratelimiter.methods.tokenbucket;

import com.example.sampleapplication.exception.ratelimiter.RateLimitExceededException;
import com.example.sampleapplication.ratelimiter.methods.tokenbucket.requesttoken.RequestTokenBucket;
import com.example.sampleapplication.ratelimiter.methods.tokenbucket.requesttoken.RequestTokenBucketRepo;
import com.example.sampleapplication.ratelimiter.methods.tokenbucket.requesttoken.TokenGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.LocalTime;
import java.util.HashMap;

import static java.lang.Math.floor;


@Component
public class TokenBucket{

    @Autowired
    RequestTokenBucketRepo repo;



    HashMap<String, TokenGenerationService> activeTokenGenerationService;


    TokenBucket(){
        activeTokenGenerationService=new HashMap<>();
    }



    public void limitRequest(String id, double tokenRatePerSecond, long tokenBucketCapacity) {


        RequestTokenBucket requestTokenBucket =repo.getRequestTokenBucketById(id);
        requestTokenBucket.setLastGeneratedTokenTime(LocalTime.now());

        if(activeTokenGenerationService.get(id)==null)
        {
            activeTokenGenerationService.put(id,new TokenGenerationService(id,tokenRatePerSecond,tokenBucketCapacity,repo));
            requestTokenBucket.setTokenCount(tokenBucketCapacity);
            repo.updateRequestTokenBucketById(id,requestTokenBucket);
            activeTokenGenerationService.get(id).start();
        }


//        System.out.println(requestTokenBucket.getTokenCount());

        if(floor(requestTokenBucket.getTokenCount())==0)
        {
            throw new RateLimitExceededException();
        }

        requestTokenBucket.setTokenCount(requestTokenBucket.getTokenCount()-1);
        repo.updateRequestTokenBucketById(id, requestTokenBucket);

    }
}
