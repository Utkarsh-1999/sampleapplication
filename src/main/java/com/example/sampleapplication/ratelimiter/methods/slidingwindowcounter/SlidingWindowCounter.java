package com.example.sampleapplication.ratelimiter.methods.slidingwindowcounter;

import com.example.sampleapplication.exception.ratelimiter.RateLimitExceededException;
import com.example.sampleapplication.ratelimiter.methods.slidingwindowcounter.requestwindow.RequestWindow;
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

        RequestWindow requestWindow =repo.getRequestWindowById(id);

        List<LocalTime> expiredRequests=new ArrayList<>();

        for(LocalTime timestamp: requestWindow.getRequestCounterMap().keySet()){
            if(Duration.between(timestamp,LocalTime.now()).toMillis() >= millisecondsPerWindowFrame){
                expiredRequests.add(timestamp);
            }
        }

        for(LocalTime timestamp:expiredRequests)
            requestWindow.getRequestCounterMap().remove(timestamp);

//        System.out.println(requestWindow.getCurrentWindowSize());

        if(requestWindow.getCurrentWindowSize()>=windowSize){
            throw new RateLimitExceededException();
        }

        requestWindow.getRequestCounterMap().put(LocalTime.now(), requestWindow.getRequestCounterMap().get(LocalTime.now())!=null? requestWindow.getRequestCounterMap().get(LocalTime.now())+1 : 1 );
        repo.updateRequestWindowById(id, requestWindow);


    }


}
