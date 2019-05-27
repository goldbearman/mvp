package com.sabirovfarit.android.mvp.Dugger;


import com.sabirovfarit.android.mvp.UserActivity;
import dagger.Component;

@Component(modules = {PresenterModule.class,UserModule.class})
public interface UserComponent {
    void injectsUserActivity(UserActivity userActivity);
}
