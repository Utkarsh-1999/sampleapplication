package com.example.sampleapplication.ratelimiter.keygenerator;

import com.example.sampleapplication.ratelimiter.requestcontext.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdBasedKeyGenerator implements RateLimiterKeyGenerator{

    @Autowired
    RequestContext requestContext;

    @Override
    public RequestKey generateKey() {
        String requestId= requestContext.getRequestPath()+"-"+ requestContext.getId();
        return new RequestKey(requestId);
    }
}
