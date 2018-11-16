package com.example.emad.youtubedemo;

import com.example.emad.youtubedemo.POJOs.SubTitle;
import com.example.emad.youtubedemo.POJOs.TranScriptList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The interface You tube service.
 */
public interface YouTubeService {


    /**
     * Gets transcript list. contain all sub title that available for the video
     * you can get name and lang from it
     * must call before getSubTitle()
     * @param videoID the video id
     * @return the transcript list
     */
    @GET("timedtext?type=list")
    Call<TranScriptList> getTranscriptList(@Query("v") String videoID);

    /**
     * Gets sub title.
     * get lang and name from  getTranscriptList() they should look like what you
     * get from getTranscriptList()
     *
     * @param videoID the video id
     * @param lang    the language  example en,ar
     * @param name    the name      example English
     * @return the sub title
     */
    @GET("timedtext?type=track&fmt=srv3")
    Call<SubTitle> getSubTitle(@Query("v") String videoID, @Query("lang") String lang, @Query("name") String name);
}
