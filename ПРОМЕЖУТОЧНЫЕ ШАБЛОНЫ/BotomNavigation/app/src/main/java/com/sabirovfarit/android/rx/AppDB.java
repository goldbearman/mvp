package com.sabirovfarit.android.rx;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Word.class},version = 1)
public abstract class AppDB extends RoomDatabase {

    public abstract WordDao wordDao();

}
