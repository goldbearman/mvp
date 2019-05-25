package com.sabirovfarit.android.mvp.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

@Dao
public interface UserDao {

    @Insert
    void add(User user);

    @Insert
    void add(User... users);

    @Delete
    void delete(User user);
}
