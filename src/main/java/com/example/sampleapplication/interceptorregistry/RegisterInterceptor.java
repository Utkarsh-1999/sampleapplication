package com.example.sampleapplication.interceptorregistry;


import com.example.sampleapplication.communicationinterceptor.CommunicationInterceptor;
import com.example.sampleapplication.ratelimiter.interceptor.RateLimiterInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RegisterInterceptor implements WebMvcConfigurer {

    @Autowired
    ApplicationContext ctx;


    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new CommunicationInterceptor());
        registry.addInterceptor(new RateLimiterInterceptor(ctx));
    }
}
