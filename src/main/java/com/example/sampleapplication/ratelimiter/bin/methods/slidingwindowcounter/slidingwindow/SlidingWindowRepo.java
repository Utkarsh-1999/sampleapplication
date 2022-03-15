package com.example.sampleapplication.ratelimiter.bin.methods.slidingwindowcounter.slidingwindow;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SlidingWindowRepo {

    @Cacheable(value = "Sliding-Window-Counter",key="#id")
    public SlidingWindow getSlidingWindowById(String id){
        return new SlidingWindow();
    }

    @CachePut(value = "Sliding-Window-Counter",key="#id")
    public SlidingWindow updateSlidingWindowById(String id,SlidingWindow slidingWindow){
        return slidingWindow;
    }
}
