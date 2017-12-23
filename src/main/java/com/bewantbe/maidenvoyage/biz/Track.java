package com.bewantbe.maidenvoyage.biz;

import lombok.Data;
@Data
public class Track {
    private String sourceip;
    private String time;
    private String pageurl;
    private String loc;
    private String useragent;
    private String host;

    // getters and setters are removed for brevity.
    public String toString() {
        return String.format("[%s - %s - %s - %s]", sourceip, time, pageurl, "phone");
    }
}