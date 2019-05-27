package com.sabirovfarit.android.mvp;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Insert;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sabirovfarit.android.mvp.DB.App;
import com.sabirovfarit.android.mvp.DB.User;

import java.util.List;

import javax.inject.Inject;

public class UserActivity extends AppCompatActivity implements UsersContractView {

    private static final String TAG = "UserActivity";

    private EditText etName;
    private EditText etEmail;
    private UserAdapter adapter;
    private RecyclerView rvWord;
    public UserViewModel viewModel;

    @Inject
    UserPresenter presenter;
    @Inject
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        App.getComponent().injectsUserActivity(this);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        init();
    }

    private void init() {
        etEmail = findViewById(R.id.et_email);
        etName = findViewById(R.id.et_name);
        rvWord = findViewById(R.id.rv_word);

        findViewById(R.id.btn_add).setOnClickListener((view) -> presenter.add());
        findViewById(R.id.btn_clear).setOnClickListener((view) -> presenter.clear());

        presenter.attachView(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvWord.setLayoutManager(layoutManager);

        Log.i(TAG, "init: befor viewIsReady()");
        presenter.viewIsReady();
        Log.i(TAG, "init: after viewIsReady()");
    }

    public User getUser() {
        user.setName(etName.getText().toString());
        user.setEmail(etEmail.getText().toString());
        return user;
    }

    @Override
    public void showUsers(LiveData<List<User>> users) {
        users.observe(this, users1 -> {
            adapter = new UserAdapter(users1);
            rvWord.setAdapter(adapter);
        });
    }

    @Override
    public void showToast() {
        Toast.makeText(this, "Некоректные данные", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
