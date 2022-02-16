package com.example.sampleapplication.ratelimiter.methods.slidingwindowcounter.requestwindow;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;

@Component
public class RequestWindowRepo {

    @Cacheable(value="Sliding-Window-Counter",key="#id")
    public RequestWindowModel getRequestWindowModelById(String id){
        return new RequestWindowModel(new HashMap<>());
    }

    @CachePut(value="Sliding-Window-Counter",key="#id")
    public RequestWindowModel updateRequestWindowById(String id,HashMap<LocalTime,Long> requestCounterMap){
        return new RequestWindowModel(requestCounterMap);
    }
}
