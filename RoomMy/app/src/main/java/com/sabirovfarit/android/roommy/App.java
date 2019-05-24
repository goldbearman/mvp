package com.sabirovfarit.android.roommy;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App extends Application {

    public static App instance;

    private DB dataBase;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        dataBase = Room.databaseBuilder(this, DB.class, "database")
                .build();

    }

    public static App getInstance() {
        return instance;
    }

    public DB getDataBase() {
        return dataBase;
    }

}
