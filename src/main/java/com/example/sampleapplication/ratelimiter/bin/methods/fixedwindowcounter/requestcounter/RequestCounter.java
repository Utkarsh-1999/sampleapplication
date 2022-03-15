package com.example.sampleapplication.ratelimiter.bin.methods.fixedwindowcounter.requestcounter;

import java.io.Serializable;
import java.time.LocalTime;

public class RequestCounter implements Serializable {

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

    RequestCounter(long counter, LocalTime timestamp){

        this.counter=counter;
        this.timestamp=timestamp;

    }
}
