package com.sabirovfarit.android.rx;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

public class ModelFactory extends ViewModelProvider.NewInstanceFactory {
    private long id;
    public ModelFactory(long id) {
        this.id = id;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MyViewModel.class) {
            return (T) new MyViewModel(id);
        }
        return super.create(modelClass);
    }
}
