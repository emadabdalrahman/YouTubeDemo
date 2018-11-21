package com.example.emad.youtubedemo.POJOs.YTSubTitle;

import org.simpleframework.xml.Attribute;


public class Track {

    @Attribute(name = "id", required = false)
    private String ID;
    @Attribute(name = "name", required = false)
    private String Name;
    @Attribute(name = "lang_code", required = false)
    private String LangCode;
    @Attribute(name = "lang_original", required = false)
    private String LangOriginal;
    @Attribute(name = "lang_translated", required = false)
    private String LangTranslated;
    @Attribute(name = "lang_default", required = false)
    private String LangDefault;

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getLangCode() {
        return LangCode;
    }

    public String getLangOriginal() {
        return LangOriginal;
    }

    public String getLangTranslated() {
        return LangTranslated;
    }

    public String getLangDefault() {
        return LangDefault;
    }
}
