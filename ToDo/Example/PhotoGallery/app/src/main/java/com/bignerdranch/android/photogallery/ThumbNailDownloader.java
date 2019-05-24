package com.bignerdranch.android.photogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ThumbNailDownloader<T> extends HandlerThread {
    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;

    private boolean mHasQuit = false;
    private Handler mRequestHandler;
    private Handler mResponseHandler;
    private ThumbnailDownloadListener<T> mThumbnailDownloadListener;
    private ConcurrentMap<T, String> mRequestMap = new ConcurrentHashMap<>();

    public ThumbNailDownloader(Handler responseHandler) {
        super(TAG);
        // Получаем Handler из главного потока
        mResponseHandler = responseHandler;
    }

    public interface ThumbnailDownloadListener<T> {
        void onThumbnailDownloaded(T target, Bitmap thumbnail);
    }

    public void setThumbnailDownloadListener(ThumbnailDownloadListener<T> listener) {
        mThumbnailDownloadListener = listener;
    }

    @Override
    public boolean quit() {
        mHasQuit = true;
        return super.quit();
    }

    public void queueThumbnail(T target, String url) {
        Log.i(TAG, "Got a URL: " + url);
        // Фильтруем по uri
        if (url == null) {
            mRequestMap.remove(target);
        } else {
            mRequestMap.put(target, url);
            //Строим сообщение
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
                    .sendToTarget();  // и отпраляем его
        }
    }

    @Override
    protected void onLooperPrepared() {
        // Тот же Handler и обрабатывает сообщение
        mRequestHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //Проверяем WHAT и извлекаем OBJ
                if (msg.what == MESSAGE_DOWNLOAD) {
                    T target = (T) msg.obj;
                    Log.i(TAG, "Got a request for URL: " + mRequestMap.get(target));
                    handleRequest(target);
                }
            }
        };
    }
    private void handleRequest(final T target) {
        try {
            //Получаем url по target
            final String url = mRequestMap.get(target);
            if (url == null) {
                return;
            }
            byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory
                    .decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            Log.i(TAG, "Bitmap created");
            //поскольку mResponseHandler связывается с Looper главного потока, весь код run() будет выполнен в главном потоке.
            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    //Проверяем соответствие адресов
                    // Затем проверяется mHasQuit. Если выполнение ThumbnailDownloader уже завершилось, выполнение каких-либо обратных вызовов небезопасно.
                    if (mRequestMap.get(target) != url||mHasQuit) {
                        return;
                    }
                    // удаляем из requestMap связь «PhotoHolder—URL» и назначаем изображение для PhotoHolder
                    mRequestMap.remove(target);
                    mThumbnailDownloadListener.onThumbnailDownloaded(target,bitmap);
                }
            });
        } catch (IOException ioe) {
            Log.e(TAG, "Error downloading image", ioe);
        }
    }

    public void clearQueue() {
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
        mRequestMap.clear();

    }

}