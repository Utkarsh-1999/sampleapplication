package com.example.sampleapplication.ratelimiter.bin.methods.slidingwindowcounter;

import com.example.sampleapplication.exception.ratelimiter.RateLimitExceededException;
import com.example.sampleapplication.ratelimiter.bin.methods.slidingwindowcounter.slidingwindow.SlidingWindow;
import com.example.sampleapplication.ratelimiter.bin.methods.slidingwindowcounter.slidingwindow.SlidingWindowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.Math.max;

@Component
public class SlidingWindowCounter {
    @Autowired
    SlidingWindowRepo repo;

    public void limitRequest(String id,long windowDurationInMilliseconds, long windowSize){

        long timenow=System.currentTimeMillis();
        long currentFrameTime=timenow/1000*1000;

        long windowStartTime=timenow-windowDurationInMilliseconds;
        long windowStartFrameTime=windowStartTime/1000*1000;

        SlidingWindow slidingWindow=repo.getSlidingWindowById(id);


        long currentWindowSize=0;

        for(long time=windowStartFrameTime;time<=currentFrameTime;time+=1000){
            currentWindowSize=slidingWindow.getFrames().getOrDefault(time,0L);
        }

        currentWindowSize=max(0,currentWindowSize-(long)(slidingWindow.getFrames().getOrDefault(windowStartFrameTime,0L) *((double)(timenow-currentFrameTime))/1000));

        System.out.println(currentWindowSize);

        if(currentWindowSize>=windowSize){
            throw new RateLimitExceededException();
        }

        slidingWindow.getFrames().put(currentFrameTime,slidingWindow.getFrames().getOrDefault(currentFrameTime,0L)+1);

        repo.updateSlidingWindowById(id,slidingWindow);


    }
}
