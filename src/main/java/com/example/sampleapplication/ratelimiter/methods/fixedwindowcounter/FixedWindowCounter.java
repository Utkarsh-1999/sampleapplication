package com.example.sampleapplication.ratelimiter.methods.fixedwindowcounter;


import com.example.sampleapplication.exception.ratelimiter.RateLimitExceededException;
import com.example.sampleapplication.ratelimiter.methods.fixedwindowcounter.requestcounter.RequestCounterModel;
import com.example.sampleapplication.ratelimiter.methods.fixedwindowcounter.requestcounter.RequestCounterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

@Component
public class FixedWindowCounter{



    @Autowired
    RequestCounterRepo repo;


    public void limitRequest(String id, long fixedWindowResetDurationInMilliseconds, long fixedWindowRequestThreshold) {


        RequestCounterModel requestCounterModel= repo.getCounterById(id);

        LocalTime currentTime=LocalTime.now();
        LocalTime timestamp=requestCounterModel.getTimestamp();

        long elapsedMilliseconds=Duration.between(timestamp,currentTime).toMillis();

        if(elapsedMilliseconds>= fixedWindowResetDurationInMilliseconds){
            requestCounterModel.setCounter(0);
        }

        if(requestCounterModel.getCounter()>= fixedWindowRequestThreshold){
            throw new RateLimitExceededException();
        }

        repo.updateCounterById(id, requestCounterModel.getCounter()+1);

    }


}

