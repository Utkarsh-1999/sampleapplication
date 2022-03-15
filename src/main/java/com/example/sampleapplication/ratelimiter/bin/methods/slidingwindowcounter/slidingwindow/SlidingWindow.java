package com.example.sampleapplication.ratelimiter.bin.methods.slidingwindowcounter.slidingwindow;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashMap;

public class SlidingWindow implements Serializable {
    private HashMap<Long, Long> frames;

    public SlidingWindow(){
        frames=new HashMap<>();
    }


    public HashMap<Long, Long> getFrames() {
        return frames;
    }

    public void setFrames(HashMap<Long, Long> frames) {
        this.frames = frames;
    }
}
