package com.example.sampleapplication.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Order(1)
public class RequestTrackingFilter implements Filter {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    Logger logger= LoggerFactory.getLogger(RequestTrackingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,ServletException {

//        kafkaTemplate.send("ip_listing", request.getRemoteAddr());


        logger.info("RequestKey received from : "+request.getRemoteAddr()+" : "+request.getRemotePort());
        filterChain.doFilter(request,response);
    }


}
