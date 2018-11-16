package com.example.emad.youtubedemo.POJOs;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "transcript_list", strict = false)
public class TranScriptList {

    @ElementList(name = "track", required = false, inline = true)
    private List<Track> trackList;

    @Attribute(name = "docid", required = false)
    private String DocID;

    public List<Track> getTrackList() {
        return trackList;
    }

    public String getDocID() {
        return DocID;
    }
}
