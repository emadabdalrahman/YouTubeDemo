package com.example.emad.youtubedemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.emad.youtubedemo.POJOs.YTSearch.Item;

import java.lang.ref.WeakReference;
import java.util.List;

public class SearchResultAdapter extends Adapter<SearchResultAdapter.VHolder> {

    private List<Item> mItems;
    private WeakReference<Context> mContext;
    private ItemListener mListener;

    /**
     * Instantiates a new Search result adapter.
     *
     * @param context  the context
     * @param items    the items
     * @param listener the listener
     */
    public SearchResultAdapter(Context context, List<Item> items, ItemListener listener) {
        this.mItems = items;
        this.mContext = new WeakReference<>(context);
        this.mListener = listener;
    }

    /**
     * Update adapter.
     *
     * @param items the items
     */
    public void updateAdapter(List<Item> items) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(this.mItems,items));
        this.mItems.clear();
        this.mItems = items;
        result.dispatchUpdatesTo(this);
    }

    /**
     * Sets listener.
     *
     * @param listener the listener
     */
    public void setListener(ItemListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_video, viewGroup,false);
        return new VHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder vHolder, int i) {
        vHolder.mTitle.setText(mItems.get(i).getSnippet().getTitle());
        vHolder.mSubTitle.setText(mItems.get(i).getSnippet().getChannelTitle());
        Glide.with(mContext.get())
                .load(mItems.get(i).getSnippet().getThumbnails().getHigh().getUrl())
                .into(vHolder.mThumbnail);
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull VHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mContext.clear();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * The interface Item listener.
     */
    public interface ItemListener {
        /**
         * On item click listener.
         *
         * @param position the position
         */
        void onItemClickListener(int position);
    }

    /**
     *  View holder.
     */
    public static class VHolder extends RecyclerView.ViewHolder {

        ImageView mThumbnail;
        TextView mTitle;
        TextView mSubTitle;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         * @param listener the listener
         */
        public VHolder(@NonNull View itemView, final ItemListener listener) {
            super(itemView);
            mThumbnail = itemView.findViewById(R.id.video_thumbnail);
            mTitle = itemView.findViewById(R.id.video_title);
            mSubTitle = itemView.findViewById(R.id.video_sub_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(getLayoutPosition());
                }
            });
        }
    }

    /**
     * The type Diff callback.
     */
    public class DiffCallback extends DiffUtil.Callback {


        List<Item> mOldSet;
        List<Item> mNewSet;

        /**
         * Instantiates a new Diff callback.
         *
         * @param oldSet the old set
         * @param newSet the new set
         */
        public DiffCallback(List<Item> oldSet, List<Item> newSet) {
            this.mOldSet = oldSet;
            this.mNewSet = newSet;
        }

        @Override
        public int getOldListSize() {
            return mOldSet.size();
        }

        @Override
        public int getNewListSize() {
            return mNewSet.size();
        }

        @Override
        public boolean areItemsTheSame(int i, int i1) {
            return mOldSet.get(i) == mNewSet.get(i1);
        }

        @Override
        public boolean areContentsTheSame(int i, int i1) {
            return mOldSet.get(i).equals(mNewSet.get(i1));
        }
    }
}
