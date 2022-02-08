package com.example.sampleapplication.view.data;


import java.io.Serializable;

public class DataView implements Serializable {
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;
}
