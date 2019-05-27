package com.sabirovfarit.android.mvp.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = User.class,version = 1)
public abstract class AppDB extends RoomDatabase {

    public abstract UserDao userDao();
}
