package com.example.sampleapplication.ratelimiter.methods.slidingwindowcounter;

import com.example.sampleapplication.exception.ratelimiter.RateLimitExceededException;
import com.example.sampleapplication.ratelimiter.methods.slidingwindowcounter.requestwindow.RequestWindowModel;
import com.example.sampleapplication.ratelimiter.methods.slidingwindowcounter.requestwindow.RequestWindowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SlidingWindowCounter {

    @Autowired
    RequestWindowRepo repo;

    public void limitRequest(String id, long millisecondsPerWindowFrame, long windowSize) {

        RequestWindowModel requestWindowModel=repo.getRequestWindowModelById(id);

        List<LocalTime> expiredRequests=new ArrayList<>();

        for(LocalTime timestamp:requestWindowModel.getRequestCounterMap().keySet()){
            if(Duration.between(timestamp,LocalTime.now()).toMillis() >= millisecondsPerWindowFrame){
                expiredRequests.add(timestamp);
            }
        }

        for(LocalTime timestamp:expiredRequests)
            requestWindowModel.getRequestCounterMap().remove(timestamp);

//        System.out.println(requestWindowModel.getCurrentWindowSize());

        if(requestWindowModel.getCurrentWindowSize()>=windowSize){
            throw new RateLimitExceededException();
        }

        requestWindowModel.getRequestCounterMap().put(LocalTime.now(), requestWindowModel.getRequestCounterMap().get(LocalTime.now())!=null?requestWindowModel.getRequestCounterMap().get(LocalTime.now())+1 : 1 );
        repo.updateRequestWindowById(id,requestWindowModel.getRequestCounterMap());


    }


}
