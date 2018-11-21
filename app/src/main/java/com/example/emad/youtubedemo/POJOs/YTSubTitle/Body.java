package com.example.emad.youtubedemo.POJOs.YTSubTitle;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "body", strict = false)
public class Body {
    @ElementList(name = "p", required = false, inline = true)
    private List<TimedText> timeList;

    public List<TimedText> getTimeList() {
        return timeList;
    }
}
