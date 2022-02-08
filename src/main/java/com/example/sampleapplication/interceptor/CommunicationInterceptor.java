package com.example.sampleapplication.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CommunicationInterceptor implements HandlerInterceptor {



    Logger logger=LoggerFactory.getLogger(HandlerInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
        logger.info(request.getRemoteAddr()+" invoked : "+request.getMethod());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,HttpServletResponse response,Object handler,ModelAndView modelAndView) throws  Exception{
        logger.info("Server responded with content: "+response.getContentType()+" to "+request.getRemoteAddr()+" with status : "+response.getStatus());

    }

    @Override
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response,Object handler,Exception exception) throws Exception{
        logger.info("Server completed communication with "+request.getRemoteAddr());
    }




}
