package com.sabirovfarit.android.mvp.Dugger;

import com.sabirovfarit.android.mvp.UserModel;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModelModule {

    @Provides
    UserModel provideUserModel() {
        return new UserModel();
    }
}
