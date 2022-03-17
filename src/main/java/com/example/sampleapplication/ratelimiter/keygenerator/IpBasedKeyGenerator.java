package com.example.sampleapplication.ratelimiter.keygenerator;

import com.example.sampleapplication.ratelimiter.clientip.ClientIpHandler;
import com.example.sampleapplication.ratelimiter.requestcontext.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class IpBasedKeyGenerator implements RateLimiterKeyGenerator {
    @Autowired
    ClientIpHandler clientIpHandler;


    @Autowired
    RequestContext requestContext;

    @Override
    public RequestKey generateKey() {

        String requestId= requestContext.getRequestPath()+"-"+requestContext.getIp();
        return new RequestKey(requestId);
    }
}
