package com.sabirovfarit.android.rx;

import android.arch.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private long id;
    public MyViewModel(long id) {
        this.id = id;
    }
}
