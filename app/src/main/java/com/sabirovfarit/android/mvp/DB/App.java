package com.sabirovfarit.android.mvp.DB;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App extends Application {

    private static final String TAG = "App";


    private static App instance;
    private AppDB mDataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mDataBase = Room.databaseBuilder(this, AppDB.class, "database")
                .build();
    }
}
