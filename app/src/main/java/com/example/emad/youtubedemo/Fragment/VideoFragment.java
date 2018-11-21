package com.example.emad.youtubedemo.Fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emad.youtubedemo.POJOs.YTSubTitle.SubTitle;
import com.example.emad.youtubedemo.POJOs.YTSubTitle.TimedText;
import com.example.emad.youtubedemo.R;
import com.example.emad.youtubedemo.VModel.SubTitleVM;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;

public class VideoFragment extends Fragment implements YouTubePlayer.OnInitializedListener {

    TextView textView;
    YouTubePlayer mYouTubePlayer;
    LiveData<SubTitle> mSubTitleLiveData;
    String mVideoID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);

        mVideoID = getArguments().getString("VideoID");
        textView = rootView.findViewById(R.id.test);

        //find youtube fragment
        YouTubePlayerSupportFragment youTubePlayerView = (YouTubePlayerSupportFragment) getChildFragmentManager()
                .findFragmentById(R.id.youTubePlayerFragment);

        //initialize youtube fragment
        youTubePlayerView.initialize("YouTubeDemoApp", this);

        //create ViewModel instant from this activity to hold data
        SubTitleVM subTitleVM = ViewModelProviders.of(this).get(SubTitleVM.class);

        // give LiveData<Subtitle> observer, onChanged call when subtitle data changed
        mSubTitleLiveData = subTitleVM.getSubTitle(mVideoID);
        mSubTitleLiveData.observe(this, new Observer<SubTitle>() {
            @Override
            public void onChanged(@Nullable SubTitle subTitle) {
                updateSubtitleText(subTitle);
                ArrayList<Integer> result = startSearch(subTitle);
                if (result.size() != 0) {
                    // get median position
                    int mid = result.size() / 2;
                    if (mYouTubePlayer != null) {
                        //move video to median position
                        mYouTubePlayer.seekToMillis(result.get(mid));
                    }
                }
            }
        });

        return rootView;
    }

    /**
     * build and show subtitle text.
     *
     * @param subTitle the sub title
     */
    public void updateSubtitleText(SubTitle subTitle) {
        //build subtitle string to show
        StringBuilder builder = new StringBuilder();
        for (TimedText t : subTitle.getBody().getTimeList()) {
            if (t.getText() != null)// some times text will be null
                builder.append(t.getText()).append("\n");
        }
        // show subtitle in textview
        textView.setText(builder.toString());
    }


    /**
     * Start search return array list hold times where you can find search text
     *
     * @param subTitle the sub title
     * @return the array list
     */
    public ArrayList<Integer> startSearch(SubTitle subTitle) {

        //create array list
        ArrayList<Integer> resultPosition = new ArrayList<>();

        //get search text that has been send from SearchFragment
        String searchText = getArguments().getString("q");
        if (searchText != null) {

            boolean isFound = false;// flag for toast massage
            // collect all time where search text found in
            for (int i = 0; i < subTitle.getBody().getTimeList().size(); i++) {
                TimedText timedText = subTitle.getBody().getTimeList().get(i);
                if (timedText.getText() != null && timedText.getText().toLowerCase().contains(searchText)) {
                    resultPosition.add(Integer.parseInt(timedText.getStartTime()));
                    isFound = true;
                }
            }
            // if search text not found in subtitle then show toast massage
            if (!isFound) {
                Toast.makeText(getContext(), "Text not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            //empty text
            // show toast massage
            Toast.makeText(getContext(), "Empty Search Text", Toast.LENGTH_SHORT).show();
        }
        return resultPosition;
    }


    //called when youtube initialize success
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        mYouTubePlayer = youTubePlayer;
        youTubePlayer.loadVideo(mVideoID);
    }

    //called when youtube initialize fail
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSubTitleLiveData.removeObservers(this);
    }
}
