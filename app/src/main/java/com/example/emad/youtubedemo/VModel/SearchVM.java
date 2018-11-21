package com.example.emad.youtubedemo.VModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.emad.youtubedemo.Model.YouTubeApiProvider;
import com.example.emad.youtubedemo.POJOs.YTSearch.YouTubeSearch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchVM extends ViewModel {

    private MutableLiveData<YouTubeSearch> mSearch;
    private String lastQ;

    public LiveData<YouTubeSearch> getYouTubeSearch(String q) {
        if (mSearch == null) {
            mSearch = new MutableLiveData<>();
            loadSearchResult(q);
        }

        if (lastQ != null && !lastQ.equals(q))
            loadSearchResult(q);

        return mSearch;
    }

    public LiveData<YouTubeSearch> getYouTubeSearch() {
        if (mSearch != null) {
            return mSearch;
        }
        return null;
    }


        /**
         * get search result from youtube API
         * @param q
         */
    private void loadSearchResult(String q) {
        lastQ = q;
        YouTubeApiProvider.getSearch(q).enqueue(new Callback<YouTubeSearch>() {
            @Override
            public void onResponse(Call<YouTubeSearch> call, Response<YouTubeSearch> response) {
                if (response.isSuccessful()) {
                    mSearch.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<YouTubeSearch> call, Throwable t) {

            }
        });
    }
}
