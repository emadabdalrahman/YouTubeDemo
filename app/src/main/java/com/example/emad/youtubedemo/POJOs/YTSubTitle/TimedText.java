package com.example.emad.youtubedemo.POJOs.YTSubTitle;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "p",strict = false)
public class TimedText {
    @Attribute(required = false, name = "t")
    private String StartTime;
    @Attribute(required = false, name = "d")
    private  String Duration;
    @Text(required = false)
    private String Text;

    public String getStartTime() {
        return StartTime;
    }

    public String getDuration() {
        return Duration;
    }

    public String getText() {
        return Text;
    }
}
