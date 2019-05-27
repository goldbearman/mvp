package com.sabirovfarit.android.mvp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.sabirovfarit.android.mvp.DB.App;
import com.sabirovfarit.android.mvp.DB.User;

import java.util.List;

public class UserViewModel extends ViewModel {

    private LiveData<List<User>> userLiveData;

    public LiveData<List<User>> getUsers() {
        if (userLiveData == null) {
            userLiveData = App.getInstance().getDataBase().userDao().getAllUsers();
        }
        return userLiveData;
    }

}
