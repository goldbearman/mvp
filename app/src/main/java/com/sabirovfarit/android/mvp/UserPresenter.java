package com.sabirovfarit.android.mvp;

import android.arch.lifecycle.LiveData;
import android.text.TextUtils;
import android.util.Log;

import com.sabirovfarit.android.mvp.DB.User;

import java.util.List;

public class UserPresenter {

    private static final String TAG = "UserActivity";

    private UsersContractView view;
    private UserModel model;

    public UserPresenter(UserModel model) {
        this.model = model;
    }

    public void attachView(UsersContractView view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    public void viewIsReady() {
        Log.i(TAG, "UserPresenter viewIsReady: ");
        loadUsers();
    }

    private void loadUsers() {
        Log.i(TAG, "UserPresenter loadUsers: ");
        view.showUsers(model.loadUsers());
    }

    public void add() {
        User user = view.getUser();
        if (TextUtils.isEmpty(user.getName()) || TextUtils.isEmpty(user.getEmail())) {
            view.showToast();
            return;
        }
        model.addUser(user);
    }

    public void clear() {
        model.clearUsers();

    }
}
