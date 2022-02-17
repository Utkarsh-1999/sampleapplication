package com.example.sampleapplication.ratelimiter.methods.leakybucket.bucket;

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

    private LocalTime queueFrontTime;

    public LocalTime getQueueFrontTime() {
        return queueFrontTime;
    }

    public void setQueueFrontTime(LocalTime queueFrontTime) {
        this.queueFrontTime = queueFrontTime;
    }

    Bucket(){
        requestQueue=new LinkedList<>();
        queueFrontTime=null;

    }
}
