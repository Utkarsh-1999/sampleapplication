package com.example.sampleapplication.ratelimiter.interceptor;

import com.example.sampleapplication.ratelimiter.clientip.ClientIpHandler;
import com.example.sampleapplication.ratelimiter.requestcontext.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class RateLimiterInterceptor implements HandlerInterceptor {



    ApplicationContext ctx;

    public RateLimiterInterceptor(ApplicationContext ctx){
        this.ctx=ctx;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{


        RequestContext requestContext=ctx.getBean(RequestContext.class);


        ClientIpHandler clientIpHandler=ctx.getBean(ClientIpHandler.class);

        requestContext.setIp(clientIpHandler.getClientIp(request));
        return true;
    }
}
