package com.example.emad.youtubedemo;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.emad.youtubedemo.POJOs.SubTitle;
import com.example.emad.youtubedemo.POJOs.TimedText;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import static com.example.emad.youtubedemo.MainVM.VIDEO_ID;


public class MainActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {


    TextView textView;
    TextInputEditText mEditText;
    YouTubePlayerFragment youTubePlayerView;
    SubTitle mSubTitle;
    YouTubePlayer mYouTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.textInputEditText);
        textView = findViewById(R.id.test);

        //find youtube fragment
        youTubePlayerView = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youTubePlayerFragment);
        //initialize youtube fragment
        youTubePlayerView.initialize("YouTubeDemoApp", this);

        //create ViewModel instant from this activity to hold data
        MainVM mainVM = ViewModelProviders.of(this).get(MainVM.class);
        // give LiveData<Subtitle> observer, onChanged call when subtitle data changed
        mainVM.getSubTitle().observe(this, new Observer<SubTitle>() {
            @Override
            public void onChanged(@Nullable SubTitle subTitle) {
                mSubTitle = subTitle;

                //build subtitle string to show
                StringBuilder builder = new StringBuilder();
                for (TimedText t : subTitle.getBody().getTimeList()) {
                    builder.append(t.getText()).append("\n");
                }
                // show subtitle in textview
                textView.setText(builder.toString());
                initSearchButton();
            }
        });
    }

    private void initSearchButton() {
        Button button =
                findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
                hideKeyboard();
            }
        });
    }


    /**
     * search for text from TextInputEditText in SubTitle
     * and move video to first found position.
     * show massage for empty text and not found text
     */
    private void startSearch() {
        // get text from input edit text
        String searchText = mEditText.getText().toString().toLowerCase();
        if (!searchText.equals("")) {
            // if text no empty
            boolean isFound = false;
            for (TimedText timedText : mSubTitle.getBody().getTimeList()) {
                // search for text in subtitle
                // and move to first subtitle contain the search text
                // search text maybe font at many postion but for move video for first one
                if (timedText.getText().toLowerCase().contains(searchText)) {
                    if (mYouTubePlayer != null)
                        mYouTubePlayer.seekToMillis(Integer.parseInt(timedText.getStartTime()));
                    isFound = true;
                    break;
                }
            }
            // if search text not found in subtitle then show toast massage
            if (!isFound) {
                Toast.makeText(this,"Text not found",Toast.LENGTH_SHORT).show();
            }
        } else {
            //empty text
            // show toast massage
            Toast.makeText(this,"Empty Search Text",Toast.LENGTH_SHORT).show();
        }
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //called when youtube initialize success
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        // start video with id = -qnyd7Ht9uc
        youTubePlayer.loadVideo(VIDEO_ID);
        mYouTubePlayer = youTubePlayer;
    }

    //called when youtube initialize fail
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
