package com.example.emad.youtubedemo.Model;

import com.example.emad.youtubedemo.POJOs.YTSearch.YouTubeSearch;
import com.example.emad.youtubedemo.POJOs.YTSubTitle.SubTitle;
import com.example.emad.youtubedemo.POJOs.YTSubTitle.TranScriptList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The interface You tube service.
 */
public interface YouTubeApiService {


    /**
     * Gets transcript list. contain all sub title that available for the video
     * you can get name and lang from it
     * must call before getSubTitle()
     *
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

    /**
     * search for text by using Youtube API
     * Default data in request : maxResults = 25 number of return videos in response
     *                           type = video  type of return data
     *                           videoCaption = closedCaption  return only videos had subtitles
     * @param q search text
     * @return
     */
    @GET("search?part=snippet&maxResults=25&type=video&videoCaption=closedCaption&key=AIzaSyArgOELR6MO_nb4ANDLLmTk6COi4MmIqxM")
    Call<YouTubeSearch> getSearch(@Query("q") String q);
}
