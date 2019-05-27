package com.sabirovfarit.android.mvp.Dugger;

import com.sabirovfarit.android.mvp.DB.User;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    @Provides
    User provideUser() {
        return new User();
    }
}
