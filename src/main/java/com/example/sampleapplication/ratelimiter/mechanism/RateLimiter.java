package com.example.sampleapplication.ratelimiter.mechanism;

import com.example.sampleapplication.ratelimiter.keygenerator.RequestKey;

public interface RateLimiter {
    void tryAcceptRequest(RequestKey requestKey);
    void tryIncrementAllowedRequest(RequestKey requestKey);
}
