package com.sabirovfarit.android.mvp.Dugger;

import com.sabirovfarit.android.mvp.UserModel;
import com.sabirovfarit.android.mvp.UserPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    UserPresenter provideUserProvider(UserModel userModel) {
        return new UserPresenter(userModel);
    }
    @Provides
    UserModel provideUserModel() {
        return new UserModel();
    }

}
