package com.example.emad.youtubedemo.VModel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.widget.Toast;


import com.example.emad.youtubedemo.POJOs.YTSubTitle.SubTitle;
import com.example.emad.youtubedemo.POJOs.YTSubTitle.Track;
import com.example.emad.youtubedemo.POJOs.YTSubTitle.TranScriptList;
import com.example.emad.youtubedemo.Model.YouTubeApiProvider;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The type Main vm.
 */
public class SubTitleVM extends AndroidViewModel {

    private MutableLiveData<SubTitle> mSubTitle;

    public SubTitleVM(@NonNull Application application) {
        super(application);
    }

    /**
     * Gets sub title.
     *
     * @return the sub title
     */
    public LiveData<SubTitle> getSubTitle(String videoID) {
        if (mSubTitle == null) {
            mSubTitle = new MutableLiveData<SubTitle>();
            loadTranScriptList(videoID);
        }
        return mSubTitle;
    }


    /**
     * find all translated script for video
     */
    private void loadTranScriptList(final String videoID) {
        YouTubeApiProvider.getTranScriptList(videoID).enqueue(new Callback<TranScriptList>() {
            @Override
            public void onResponse(Call<TranScriptList> call, Response<TranScriptList> response) {
                if (response.isSuccessful()) {
                    if (response.body().getTrackList() == null) {
                        Toast.makeText(getApplication().getApplicationContext(), "empty subtitle", Toast.LENGTH_SHORT).show();
                    } else {
                        Track track = getDefaultTrack(response.body().getTrackList());
                        if (track != null)
                            loadSubTitle(videoID, track.getLangCode(), track.getName());
                        else
                            Toast.makeText(getApplication().getApplicationContext(), "no default subtitle", Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onFailure(Call<TranScriptList> call, Throwable t) {
            }
        });

    }

    /**
     * search in track list for default translated script
     * just for now app will work with default script
     * note: sometimes their is no default script but there is
     * another script for now app will use default
     */
    private Track getDefaultTrack(List<Track> tracks) {
        if (tracks != null) {
            for (int i = 0; i < tracks.size(); i++) {
                String default_lang = tracks.get(i).getLangDefault();
                if (default_lang != null && default_lang.equals("true")) {
                    return tracks.get(i);
                }
            }
        }
        return null;
    }

    private void loadSubTitle(String videoID, String lang, String name) {
        YouTubeApiProvider.getSubTitle(videoID, lang, name).enqueue(new Callback<SubTitle>() {
            @Override
            public void onResponse(Call<SubTitle> call, Response<SubTitle> response) {
                if (response.isSuccessful() && response.body() != null)
                    //give live data subtitle
                    mSubTitle.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SubTitle> call, Throwable t) {

            }
        });
    }
}
