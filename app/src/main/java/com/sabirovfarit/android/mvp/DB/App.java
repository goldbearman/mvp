package com.sabirovfarit.android.mvp.DB;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.sabirovfarit.android.mvp.Dugger.DaggerUserComponent;
import com.sabirovfarit.android.mvp.Dugger.UserComponent;

public class App extends Application {

    private static final String TAG = "App";

    private static App instance;
    private AppDB mDataBase;
    private static UserComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerUserComponent.create();

        mDataBase = Room.databaseBuilder(this, AppDB.class, "database")
                .build();
    }

    public static UserComponent getComponent() {
        return component;
    }

    public static App getInstance() {
        return instance;
    }

    public static void setInstance(App instance) {
        App.instance = instance;
    }

    public AppDB getDataBase() {
        return mDataBase;
    }

    public void setDataBase(AppDB dataBase) {
        mDataBase = dataBase;
    }
}
