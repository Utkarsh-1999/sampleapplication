package com.example.sampleapplication.ratelimiter.bin.methods.tokenbucket.requesttoken;

import java.io.Serializable;
import java.time.LocalTime;

public class RequestTokenBucket implements Serializable {

    private double tokenCount;

    private LocalTime lastGeneratedTokenTime;



    public LocalTime getLastGeneratedTokenTime() {
        return lastGeneratedTokenTime;
    }

    public void setLastGeneratedTokenTime(LocalTime lastGeneratedTokenTime) {
        this.lastGeneratedTokenTime = lastGeneratedTokenTime;
    }

    RequestTokenBucket(){
        this.tokenCount=0;
        this.lastGeneratedTokenTime=LocalTime.now();
    }

    public double getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(double tokenCount) {
        this.tokenCount = tokenCount;
    }


}
