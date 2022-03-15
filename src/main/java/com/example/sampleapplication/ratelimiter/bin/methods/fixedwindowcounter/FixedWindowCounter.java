package com.example.sampleapplication.ratelimiter.bin.methods.fixedwindowcounter;


import com.example.sampleapplication.exception.ratelimiter.RateLimitExceededException;
import com.example.sampleapplication.ratelimiter.bin.methods.fixedwindowcounter.requestcounter.RequestCounter;
import com.example.sampleapplication.ratelimiter.bin.methods.fixedwindowcounter.requestcounter.RequestCounterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

@Component
public class FixedWindowCounter{



    @Autowired
    RequestCounterRepo repo;


    public void limitRequest(String id, long fixedWindowResetDurationInMilliseconds, long fixedWindowRequestThreshold) {


        RequestCounter requestCounter = repo.getRequestCounterById(id);

        LocalTime currentTime=LocalTime.now();
        LocalTime timestamp= requestCounter.getTimestamp();

        long elapsedMilliseconds=Duration.between(timestamp,currentTime).toMillis();

        if(elapsedMilliseconds>= fixedWindowResetDurationInMilliseconds){
            requestCounter.setCounter(0);
            requestCounter.setTimestamp(LocalTime.now());
        }

        if(requestCounter.getCounter()>= fixedWindowRequestThreshold){
            throw new RateLimitExceededException();
        }

        requestCounter.setCounter(requestCounter.getCounter()+1);
        repo.updateRequestCounterById(id, requestCounter);

    }


}

