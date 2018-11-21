package com.example.emad.youtubedemo.Model;

import com.example.emad.youtubedemo.POJOs.YTSearch.YouTubeSearch;
import com.example.emad.youtubedemo.POJOs.YTSubTitle.SubTitle;
import com.example.emad.youtubedemo.POJOs.YTSubTitle.TranScriptList;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class YouTubeApiProvider {

    private static final String SEARCH_BASE_API = "https://www.googleapis.com/youtube/v3/";
    private static final String SUB_TITLE_BASE_URL = "https://www.youtube.com/api/";

    public static Call<YouTubeSearch> getSearch(String q) {
        //build retrofit request
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SEARCH_BASE_API)
                //add gson factory to convert response from json to YouTubeSearch Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create youtube service.
        YouTubeApiService youTubeApiService = retrofit.create(YouTubeApiService.class);
        return youTubeApiService.getSearch(q);
    }

    public static Call<SubTitle> getSubTitle(String videoId, String lang, String name) {
        //build retrofit request
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SUB_TITLE_BASE_URL)
                //SimpleXmlConverterFactory is deprecated and recommend switching to the JAXB converter.
                //but JAXB converter not support Android so stay with SimpleXmlConverterFactory
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(new Persister(new AnnotationStrategy())))
                .build();

        // create youtube service.
        YouTubeApiService youTubeApiService = retrofit.create(YouTubeApiService.class);
        return youTubeApiService.getSubTitle(videoId, lang, name);
    }

    public static Call<TranScriptList> getTranScriptList(String videoID) {
        //build retrofit request
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SUB_TITLE_BASE_URL)
                //SimpleXmlConverterFactory is deprecated and recommend switching to the JAXB converter.
                //but JAXB converter not support Android so stay with SimpleXmlConverterFactory
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(new Persister(new AnnotationStrategy())))
                .build();

        // create youtube service.
        YouTubeApiService youTubeApiService = retrofit.create(YouTubeApiService.class);
        return youTubeApiService.getTranscriptList(videoID);
    }
}
