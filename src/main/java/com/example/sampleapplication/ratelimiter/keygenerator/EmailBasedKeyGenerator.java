package com.example.sampleapplication.ratelimiter.keygenerator;

import com.example.sampleapplication.ratelimiter.requestcontext.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailBasedKeyGenerator implements RateLimiterKeyGenerator{

    @Autowired
    RequestContext requestContext;

    @Override
    public RequestKey generateKey() {
        String requestId= requestContext.getPrefix()+"-"+ requestContext.getSuffix();
        return new RequestKey(requestId);
    }
}
