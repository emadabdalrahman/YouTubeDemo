package com.example.emad.youtubedemo;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


import com.example.emad.youtubedemo.POJOs.SubTitle;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * The type Main vm.
 */
public class MainVM extends ViewModel {
    //basic youtube api url
    private static final String BASE_URL = "https://www.youtube.com/api/";
    /**
     * The constant VIDEO_ID.
     */
    //video id to make test on it
    public static final String VIDEO_ID = "-qnyd7Ht9uc";

    private MutableLiveData<SubTitle> mSubTitle;

    /**
     * Gets sub title.
     *
     * @return the sub title
     */
    public LiveData<SubTitle> getSubTitle() {
        if (mSubTitle == null) {
            mSubTitle = new MutableLiveData<SubTitle>();
            loadSubTitle();
        }
        return mSubTitle;
    }

    private void loadSubTitle() {

        //build retrofit request
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //SimpleXmlConverterFactory is deprecated and recommend switching to the JAXB converter.
                //but JAXB converter not support Android so stay with SimpleXmlConverterFactory
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(new Persister(new AnnotationStrategy())))
                .build();

        // create youtube service.
        YouTubeService youTubeService = retrofit.create(YouTubeService.class);
        //get subtitle with  id="-qnyd7Ht9uc" lang= "en" name=""
        //request look like  https://www.youtube.com/api/timedtext?type=track&fmt=srv3&v=-qnyd7Ht9uc&lang=en&name=
        Call<SubTitle> call = youTubeService.getSubTitle(VIDEO_ID, "en", "");
        call.enqueue(new Callback<SubTitle>() {
            @Override
            public void onResponse(Call<SubTitle> call, Response<SubTitle> response) {
                if (response.isSuccessful())
                    //give live data subtitle
                    mSubTitle.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SubTitle> call, Throwable t) {

            }
        });
    }
}
