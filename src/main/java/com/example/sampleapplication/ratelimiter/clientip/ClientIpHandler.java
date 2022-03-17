package com.example.sampleapplication.ratelimiter.clientip;

import com.example.sampleapplication.exception.ratelimiter.BotEncounteredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
public class ClientIpHandler {

    @Value("${ratelimiter.clientip.bot-threshold:0}")
    int botThreshold;



    public String getClientIp(HttpServletRequest request){


        int botScore=1;
        boolean verifiedBot =false;


        if(request.getHeader("Cf-Bot-Score")!=null){
            botScore= Integer.parseInt(request.getHeader("Cf-Bot-Score"));
        }

        if(request.getHeader("Cf-Bot-Verified")!=null){
            verifiedBot =Boolean.parseBoolean(request.getHeader("Cf-Bot-Verified"));
        }

        if(botScore>0 && botScore<=botThreshold && !verifiedBot){
            throw new BotEncounteredException();
        }

        String forwardedFor=request.getHeader("X-Forwarded-For");

        String [] ips={};

        if(forwardedFor!=null)
        {
            forwardedFor=forwardedFor.replaceAll(" ","");
            ips=forwardedFor.split(",");
        }





        int ipCount = ips.length;

        if((forwardedFor!=null && forwardedFor.equals("")) || ipCount==0){
            return request.getRemoteHost();
        }
        else if(ipCount<3)
        {
            return ips[0];
        }

        return  ips[ipCount-3];

    }
}
