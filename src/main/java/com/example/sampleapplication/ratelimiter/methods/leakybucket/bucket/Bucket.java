package com.example.sampleapplication.ratelimiter.methods.leakybucket.bucket;

import com.example.sampleapplication.ratelimiter.methods.leakybucket.LeakyBucket;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

public class Bucket implements Serializable {

    private Queue<String> requestQueue;

    public Queue<String> getRequestQueue() {
        return requestQueue;
    }

    public void setRequestQueue(Queue<String> requestQueue) {
        this.requestQueue = requestQueue;
    }

    private LocalTime queueFrontEntryTime;

    public LocalTime getQueueFrontEntryTime() {
        return queueFrontEntryTime;
    }

    public void setQueueFrontEntryTime(LocalTime queueFrontEntryTime) {
        this.queueFrontEntryTime = queueFrontEntryTime;
    }

    Bucket(){
        requestQueue=new LinkedList<>();
        queueFrontEntryTime =null;

    }
}
