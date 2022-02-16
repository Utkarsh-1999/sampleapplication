package com.example.sampleapplication.ratelimiter.methods.tokenbucket.requesttoken;

import java.io.Serializable;
import java.time.LocalTime;

public class RequestTokenModel implements Serializable {

    private double tokenCount;
    private LocalTime timestamp;

    RequestTokenModel(double tokenCount,LocalTime timestamp){
        this.tokenCount=tokenCount;
        this.timestamp=timestamp;
    }

    public double getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(double tokenCount) {
        this.tokenCount = tokenCount;
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }
}
