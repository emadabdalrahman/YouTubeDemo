package com.example.emad.youtubedemo.Fragment;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.example.emad.youtubedemo.POJOs.YTSearch.YouTubeSearch;
import com.example.emad.youtubedemo.R;
import com.example.emad.youtubedemo.SearchResultAdapter;
import com.example.emad.youtubedemo.VModel.SearchVM;

import androidx.navigation.fragment.NavHostFragment;

public class SearchFragment extends Fragment implements Observer<YouTubeSearch>, SearchResultAdapter.ItemListener {

    LiveData<YouTubeSearch> mYouTubeSearchLiveData;
    TextInputEditText mInputEditText;
    RecyclerView mRecyclerView;
    SearchResultAdapter mResultAdapter;
    SearchVM searchVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        mInputEditText = rootView.findViewById(R.id.textInputEditText);

        searchVM = ViewModelProviders.of(this).get(SearchVM.class);

        initSearchButton(rootView);
        initRecyclerView(rootView);

        return rootView;
    }

    private void initRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.result_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setRecycleChildrenOnDetach(true);
        mRecyclerView.setLayoutManager(layoutManager);

        if (mResultAdapter != null) {
            mRecyclerView.setAdapter(mResultAdapter);
        } else {
            mYouTubeSearchLiveData = searchVM.getYouTubeSearch();
            if (mYouTubeSearchLiveData != null) {
                mResultAdapter = new SearchResultAdapter(getContext(), mYouTubeSearchLiveData.getValue().getItems(), this);
                mRecyclerView.setAdapter(mResultAdapter);
            }
        }
    }

    private void initSearchButton(View view) {
        ImageButton button = view.findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
                hideKeyboard();
            }
        });
    }

    String mSearchText;

    public void startSearch() {
        String searchText = mInputEditText.getText().toString();
        if (!searchText.equals("")) {
            mSearchText = searchText;

            mYouTubeSearchLiveData = searchVM.getYouTubeSearch(searchText);
            mYouTubeSearchLiveData.observe(this, this);
        }
    }

    /**
     * Hide keyboard.
     */
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(getContext());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onChanged(@Nullable YouTubeSearch youTubeSearch) {
        mResultAdapter = new SearchResultAdapter(getContext(), youTubeSearch.getItems(), this);
        mRecyclerView.setAdapter(mResultAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mYouTubeSearchLiveData != null)
            mYouTubeSearchLiveData.removeObservers(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecyclerView.setAdapter(null);
        mResultAdapter.setListener(null);
        mResultAdapter = null;
    }

    @Override
    public void onItemClickListener(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("VideoID", mYouTubeSearchLiveData.getValue().getItems().get(position).getId().getVideoId());
        bundle.putString("q", mSearchText);
        NavHostFragment.findNavController(this).navigate(R.id.videoFragment, bundle);
    }
}
