package com.example.sampleapplication.ratelimiter.methods.fixedwindowcounter.requestcounter;

import java.io.Serializable;
import java.time.LocalTime;

public class RequestCounterModel implements Serializable {

    private long counter;

    private LocalTime timestamp;

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    RequestCounterModel(long counter, LocalTime timestamp){

        this.counter=counter;
        this.timestamp=timestamp;

    }
}
