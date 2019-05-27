package com.sabirovfarit.android.mvp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.sabirovfarit.android.mvp.DB.App;
import com.sabirovfarit.android.mvp.DB.User;
import com.sabirovfarit.android.mvp.DB.UserDao;

import java.util.List;
import java.util.concurrent.Executors;

public class UserModel {
    private static final String TAG = "UserActivity";

   private UserDao userDao = App.getInstance().getDataBase().userDao();

    MutableLiveData<String> data;

    public LiveData<List<User>> loadUsers() {
        Log.i(TAG, "UserModel loadUsers: ");
        return App.getInstance().getDataBase().userDao().getAllUsers();
    }

    public void addUser(User user) {
        Executors.newSingleThreadScheduledExecutor().execute(()->userDao.add(user));
    }

    public void clearUsers() {
        Executors.newSingleThreadScheduledExecutor().execute(()->userDao.deleteAll());
    }
}
