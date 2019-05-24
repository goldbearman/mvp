package com.bignerdranch.android.photogallery;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoGallaryFragment extends Fragment {

    private ThumbNailDownloader<PhotoHolder> mThumbNailDownloader;
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
        fetchItemTask.execute();
//        //Создан в главном потоке, поэтому и сообщения будут приходить в главный поток
//        Handler responseHandler = new Handler();
//        //Создаем объкт
//        mThumbNailDownloader = new ThumbNailDownloader<>(responseHandler);
//
//        mThumbNailDownloader.setThumbnailDownloadListener(new ThumbNailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
//            @Override
//            public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap bitmap) {
//                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
//                photoHolder.bindGalleryItem(drawable);
//
//            }
//        });
//        mThumbNailDownloader.start();
//        mThumbNailDownloader.getLooper();
//        Log.i(TAG, "Background thread starter");
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

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mThumbNailDownloader.quit();
//        Log.i(TAG, "Background thread destroyed");
//    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        mThumbNailDownloader.clearQueue();
//    }

    private void setupAdapter() {
        //Подтверждает, что фрагмент был присоединен к активности
        if (isAdded()) {
            mRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }

    private class FetchItemTask extends AsyncTask<Void, Void, List<GalleryItem>> {

        @Override
        protected List<GalleryItem> doInBackground(Void... voids) {
            return new FlickrFetchr().fetchItems();
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            mItems = items;
            setupAdapter();
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public PhotoHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
        }

        public void bindGalleryItem(GalleryItem galleryItem) {
            Picasso.with(getActivity())   //Context
                    .load(galleryItem.getUrl())               // Url
                    .placeholder(R.drawable.bill_up_close)    // изображение, которое должно выводиться до полной загрузки запрошенного изображения (placeholder(int) или placeholder(drawable)).
                    .into(mImageView);                        //Куда заргужать
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
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.gallery_item, parent, false);
            return new PhotoHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
            holder.bindGalleryItem(mGalleryItems.get(position));





//            Drawable placeHolder = getResources().getDrawable(R.drawable.bill_up_close);
//            mThumbNailDownloader.queueThumbnail(holder,galleryItem.getUrl());
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }



}
