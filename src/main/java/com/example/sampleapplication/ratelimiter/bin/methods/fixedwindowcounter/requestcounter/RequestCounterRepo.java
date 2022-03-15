package com.example.sampleapplication.ratelimiter.bin.methods.fixedwindowcounter.requestcounter;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class RequestCounterRepo {

    @Cacheable(value = "Fixed-Window-Counter",key="#id")
    public RequestCounter getRequestCounterById(String id) {
        return new RequestCounter(0, LocalTime.now());
    }

    @CachePut(value="Fixed-Window-Counter",key="#id")
    public RequestCounter updateRequestCounterById(String id, RequestCounter requestCounter) {
        return requestCounter;

    }


}
