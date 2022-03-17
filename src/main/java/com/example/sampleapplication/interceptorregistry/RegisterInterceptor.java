package com.example.sampleapplication.interceptorregistry;


import com.example.sampleapplication.communicationinterceptor.CommunicationInterceptor;
import com.example.sampleapplication.ratelimiter.interceptor.RateLimiterInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RegisterInterceptor implements WebMvcConfigurer {


    @Bean
    RateLimiterInterceptor getRateLimitInterceptor(){
        return new RateLimiterInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new CommunicationInterceptor());
        registry.addInterceptor(getRateLimitInterceptor());
    }
}
