package com.bignerdranch.android.photogallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoGallaryFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";

    private RecyclerView mRecyclerView;
    private List<GalleryItem> mItems = new ArrayList<>();

    public static PhotoGallaryFragment newInstance() {
        PhotoGallaryFragment fragment = new PhotoGallaryFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
       FetchItemTask fetchItemTask = new FetchItemTask();
        fetchItemTask.execute("df","fdf","dfd");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_photo_gallery, container, false);

        mRecyclerView = v.findViewById(R.id.photo_recycle_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setupAdapter();

        return v;
    }

    private void setupAdapter() {
        //Подтверждает, что фрагмент был присоединен к активности
        if (isAdded()) {
        mRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }

    private class FetchItemTask extends AsyncTask<String, Void,List<GalleryItem>> {


        @Override
        protected List<GalleryItem> doInBackground(String... strings) {
            String s = strings[1];
            return null;
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            mItems = items;
            setupAdapter();
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public PhotoHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }

        public void bindGalleryItem(GalleryItem galleryItem) {
            mTextView.setText(galleryItem.getCaption());
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        private List<GalleryItem> mGalleryItems;

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }

        @NonNull
        @Override
        public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(getActivity());
            return new PhotoHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
            holder.bindGalleryItem(galleryItem);
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }


}
