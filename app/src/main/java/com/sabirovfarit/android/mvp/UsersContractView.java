package com.sabirovfarit.android.mvp;

import android.arch.lifecycle.LiveData;

import com.sabirovfarit.android.mvp.DB.User;

import java.util.List;

public interface UsersContractView {

    User getUser();
    void showUsers(LiveData<List<User>> users);
    void showToast();
}
