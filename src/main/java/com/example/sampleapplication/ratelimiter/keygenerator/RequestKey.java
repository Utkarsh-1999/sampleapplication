package com.example.sampleapplication.ratelimiter.keygenerator;

public class RequestKey {
    private String key;

    public RequestKey(String key){
        this.key =key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
