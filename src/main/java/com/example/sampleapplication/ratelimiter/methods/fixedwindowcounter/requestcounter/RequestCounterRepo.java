package com.example.sampleapplication.ratelimiter.methods.fixedwindowcounter.requestcounter;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class RequestCounterRepo {

    @Cacheable(value = "Fixed-Window-Counter",key="#id")
    public RequestCounterModel getCounterById(String id) {
        return new RequestCounterModel(0, LocalTime.now());
    }

    @CachePut(value="Fixed-Window-Counter",key="#id")
    public RequestCounterModel updateCounterById(String id, long counter) {
        return new RequestCounterModel(counter,LocalTime.now());

    }


}
