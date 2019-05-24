package com.bignerdranch.android.photogallery;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PollService extends IntentService {

    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final String NOTIFICATION = "NOTIFICATION";

    private static final String TAG = "PollService";
    private static final long POLL_INTERVAL_MS = TimeUnit.MINUTES.toMillis(1);
    private static final String CHANNEL_ID = "10";
    public static final String ACTION_SHOW_NOTIFICATION =
            "com.bignerdranch.android.photogallery.SHOW_NOTIFICATION";
    public static final String PERM_PRIVATE =
            "com.bignerdranch.android.photogallery.PRIVATE";

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), POLL_INTERVAL_MS, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
        QueryPreferences.setAlarmOn(context,isOn);
    }



    public static boolean isServiceAlarmOn(Context context) {
        Intent i = PollService.newIntent(context);
        // если объекта не существует, вернется null
        PendingIntent pi = PendingIntent
                .getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //Провереяем, есть ли подключение
        if (!isNetvorkAvailableAndConnected()) {
            return;
        }
        String query = QueryPreferences.getStoredQuery(this);      // Получаем последий запрос(слово)
        String lastResultId = QueryPreferences.getLastResultId(this);   //последнее id полсденего запроса
        List<GalleryItem> items;

        if (query == null) {
            // нет запроса,список из последних фото
            items = new FlickrFetchr().fetchRecentPhotos();
        } else {
            // есть запрос, получаем список по запросу
            items = new FlickrFetchr().searchPhotos(query);
        }
        //если ничего нет
        if (items.size() == 0) {
            return;
        }
        // получаем id первого элемента(последнего)
        String resultId = items.get(0).getId();
        if (resultId.equals(lastResultId)) {
            Log.i(TAG, "Got an old result: " + resultId);
        } else {
            Log.i(TAG, "Got a new result: " + resultId);

            Intent i = PhotoGalleryActivity.newIntent(this);
            PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel channel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                channel = new NotificationChannel(CHANNEL_ID, "Кошачий канал",
                        NotificationManager.IMPORTANCE_HIGH);
                // Настроим канал
                channel.setDescription("Канал для напоминания о кормёжке кота");
                channel.enableLights(true);
                channel.setLightColor(Color.RED);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                notificationManager.createNotificationChannel(channel);
            }

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setTicker(getResources().getString(R.string.new_pictures_title))         //Текст бегущей строки
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)                    //Значок
                    .setContentTitle(getResources().getString(R.string.new_pictures_title))
                    .setContentText(getResources().getString(R.string.new_pictures_text))
                    .setContentIntent(pi)    //pendingIntent, который запускает активность при нажатии на уведомление
                    .setAutoCancel(true)     //уведомление исчезает, послу нажатия
                    .build();

//            //(индификатор,уведомление)
//            notificationManager.notify(0, notification);
            //Отправляем широковещательный интент
//            sendBroadcast(new Intent(ACTION_SHOW_NOTIFICATION),PERM_PRIVATE);
            showBackgroundNotification(1,notification);
        }
        //сохраняем id последней фотографии
        QueryPreferences.setLastResultId(this, resultId);
    }

    private void showBackgroundNotification(int requestCode, Notification notification) {
        Intent i = new Intent(ACTION_SHOW_NOTIFICATION);
        i.putExtra(REQUEST_CODE, requestCode);
        i.putExtra(NOTIFICATION, notification);
        sendOrderedBroadcast(i, PERM_PRIVATE, null, null,
                Activity.RESULT_OK, null, null);
    }


    private boolean isNetvorkAvailableAndConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }
}
