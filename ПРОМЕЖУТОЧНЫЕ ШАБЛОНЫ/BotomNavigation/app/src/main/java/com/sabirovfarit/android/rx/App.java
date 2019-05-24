package com.sabirovfarit.android.rx;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App extends Application {

    public static App instance;
    private AppDB database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, AppDB.class, "database")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDB getDatabase() {
        return database;
    }
}
