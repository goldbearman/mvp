package com.bignerdranch.android.photogallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class PhotoGallaryFragment extends VisibleFragment {

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
        setHasOptionsMenu(true);
        updateItems();


//        Intent i = PollService.newIntent(getActivity());
//        getActivity().startService(i);

        PollService.setServiceAlarm(getActivity(),true);

        //Создан в главном потоке, поэтому и сообщения будут приходить в главный поток
        Handler responseHandler = new Handler();
        //Создаем объкт
        mThumbNailDownloader = new ThumbNailDownloader<>(responseHandler);

        mThumbNailDownloader.setThumbnailDownloadListener(new ThumbNailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
            @Override
            public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap bitmap) {
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                photoHolder.bindDrawable(drawable);

            }
        });

        mThumbNailDownloader.start();
        mThumbNailDownloader.getLooper();
        Log.i(TAG, "Background thread starter");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbNailDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbNailDownloader.clearQueue();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_photo_gallery, menu);

        MenuItem toggleItem = menu.findItem(R.id.menu_item_toggle_polling);
        if (PollService.isServiceAlarmOn(getActivity())) {
            toggleItem.setTitle(R.string.stop_polling);
        } else {
            toggleItem.setTitle(R.string.start_polling);
        }

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "QueryTextSubmit: " + query);
                QueryPreferences.setStoredQuery(getActivity(), query);
                updateItems();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "QueryTextChange: " + newText);
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = QueryPreferences.getStoredQuery(getActivity());
                searchView.setQuery(query, false);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                QueryPreferences.setStoredQuery(getActivity(), null);
                updateItems();
                return true;
            case R.id.menu_item_toggle_polling:
                //Если объкт существует, то вернется true из PollService.isServiceAlarmOn
                boolean shouldStartAlarm = !PollService.isServiceAlarmOn
                        (getActivity());
                PollService.setServiceAlarm(getActivity(), shouldStartAlarm);
                // перезапускает меню для обновления надписи
                getActivity().invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateItems() {
        String storedQuery = QueryPreferences.getStoredQuery(getActivity());
        new FetchItemsTask(storedQuery).execute();
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, List<GalleryItem>> {

        String mQuery;

        public FetchItemsTask(String query) {
            this.mQuery = query;
        }

        @Override
        protected List<GalleryItem> doInBackground(Void... voids) {

            if (mQuery == null) {
                return new FlickrFetchr().fetchRecentPhotos();
            } else {
                return new FlickrFetchr().searchPhotos(mQuery);
            }
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            mItems = items;
            setupAdapter();
        }
    }

    private void setupAdapter() {
        //Подтверждает, что фрагмент был присоединен к активности
        if (isAdded()) {
            mRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }


    private class PhotoHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private GalleryItem mGalleryItem;

        public PhotoHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = PhotoPageActivity.newIntent(getActivity(), mGalleryItem.getPhotoPageUri());
                    startActivity(intent);
                }
            });
        }

        public void bindDrawable(Drawable drawable) {
            mImageView.setImageDrawable(drawable);
        }
        public void bindGallaryItem(GalleryItem galleryItem) {
            mGalleryItem = galleryItem;
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
            GalleryItem galleryItem = mGalleryItems.get(position);
            Drawable placeHolder = getResources().getDrawable(R.drawable.bill_up_close);
            holder.bindDrawable(placeHolder);
            holder.bindGallaryItem(galleryItem);
            mThumbNailDownloader.queueThumbnail(holder, galleryItem.getUrl());
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }


}
