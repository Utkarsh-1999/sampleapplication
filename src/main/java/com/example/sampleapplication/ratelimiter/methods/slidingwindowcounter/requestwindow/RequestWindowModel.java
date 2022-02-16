package com.example.sampleapplication.ratelimiter.methods.slidingwindowcounter.requestwindow;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashMap;


public class RequestWindowModel implements Serializable {

    private HashMap<LocalTime,Long> requestCounterMap;

    public HashMap<LocalTime, Long> getRequestCounterMap() {
        return requestCounterMap;
    }

    public void setRequestCounterMap(HashMap<LocalTime, Long> requestCounterMap) {
        this.requestCounterMap = requestCounterMap;
    }

    public long getCurrentWindowSize(){
        long currentWindowSize=0;
        for(Long count:requestCounterMap.values()){
            currentWindowSize+=count;
        }
        return currentWindowSize;

    }

    public RequestWindowModel(HashMap<LocalTime, Long> requestCounterMap) {
        this.requestCounterMap = requestCounterMap;
    }

}
