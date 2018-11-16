package com.example.emad.youtubedemo.POJOs;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false, name = "timedtext")
public class SubTitle {

    @Element(name = "body", required = false)
    private Body body;

    @Attribute(name = "format", required = false)
    private String Format;

    public Body getBody() {
        return body;
    }

    public String getFormat() {
        return Format;
    }
}

