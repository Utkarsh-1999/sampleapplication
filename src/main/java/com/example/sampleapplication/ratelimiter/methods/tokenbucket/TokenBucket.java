package com.example.sampleapplication.ratelimiter.methods.tokenbucket;

import com.example.sampleapplication.exception.ratelimiter.RateLimitExceededException;
import com.example.sampleapplication.ratelimiter.methods.tokenbucket.requesttoken.RequestTokenModel;
import com.example.sampleapplication.ratelimiter.methods.tokenbucket.requesttoken.RequestTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

import static java.lang.Double.min;
import static java.lang.Math.floor;


@Component
public class TokenBucket{

    @Autowired
    RequestTokenRepo repo;

    public void limitRequest(String id, double tokenRatePerSecond, long tokenBucketCapacity) {

        RequestTokenModel requestTokenModel=repo.getRequestTokenById(id,tokenBucketCapacity);
        LocalTime currentTime= LocalTime.now();
        LocalTime timestamp=requestTokenModel.getTimestamp();

        requestTokenModel.setTokenCount(min(tokenBucketCapacity , requestTokenModel.getTokenCount() + tokenRatePerSecond * Duration.between(timestamp,currentTime).toSeconds()));

        if(floor(requestTokenModel.getTokenCount())==0)
        {
            throw new RateLimitExceededException();
        }

        repo.updateRequestTokenById(id,requestTokenModel.getTokenCount()-1);

    }
}
