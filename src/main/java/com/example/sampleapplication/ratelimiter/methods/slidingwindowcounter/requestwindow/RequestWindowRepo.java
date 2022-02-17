package com.example.sampleapplication.ratelimiter.methods.slidingwindowcounter.requestwindow;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class RequestWindowRepo {

    @Cacheable(value="Sliding-Window-Counter",key="#id")
    public RequestWindow getRequestWindowById(String id){
        return new RequestWindow(new HashMap<>());
    }

    @CachePut(value="Sliding-Window-Counter",key="#id")
    public RequestWindow updateRequestWindowById(String id, RequestWindow requestWindow){
        return requestWindow;
    }
}
