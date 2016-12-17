package com.kashifminhaj.kmusic.ui.fragment;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kashifminhaj.kmusic.R;
import com.kashifminhaj.kmusic.ui.AlbumItem;
import com.kashifminhaj.kmusic.ui.fragment.AlbumsFragment.OnListFragmentInteractionListener;
import com.kashifminhaj.kmusic.ui.fragment.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAlbumsRecyclerViewAdapter extends RecyclerView.Adapter<MyAlbumsRecyclerViewAdapter.ViewHolder> {

    private final List<AlbumItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyAlbumsRecyclerViewAdapter(List<AlbumItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_albums, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(holder.mItem.getName());
        holder.mCountView.setText(String.valueOf(holder.mItem.getNumOfSongs()));
        try {
            holder.mArtView.setImageURI(Uri.parse(holder.mItem.getThumbnail()));
        } catch (Exception e) {
            Log.d("adapter: ", e.getMessage());
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mCountView;
        public final ImageView mArtView;
        public AlbumItem mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.album_title);
            mCountView = (TextView) view.findViewById(R.id.count);
            mArtView = (ImageView) view.findViewById(R.id.album_thumbnail);
        }


    }
}
