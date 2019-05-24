package com.sabirovfarit.android.rx;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class WordViewModel extends ViewModel {

//    private static final String TAG = "WordViewModel";
//
//    private final MutableLiveData<List<Long>> selected = new MutableLiveData<List<Long>>();

    List<Long> idsList;

    private LiveData<List<Word>> wordLiveData;
    private LiveData<List<WordsList>> ldLearnInClassTrue;
    private LiveData<List<WordsList>> ldLearnInClassFalse;

    public LiveData<List<Word>> getData() {
        if (wordLiveData == null) {
            wordLiveData = App.getInstance().getDatabase().wordDao().getAllWordsForSearchLiveData(false);
        }
        return wordLiveData;
    }

    public LiveData<List<WordsList>> getInClassTrue() {
        if (ldLearnInClassFalse == null) {
            ldLearnInClassFalse = App.getInstance().getDatabase().wordsListDao().getOnInClass(true);
        }
        return ldLearnInClassFalse;
    }
    public LiveData<List<WordsList>> getInClassFalse() {
        if (ldLearnInClassTrue == null) {
            ldLearnInClassTrue = App.getInstance().getDatabase().wordsListDao().getOnInClass(false);
        }
        return ldLearnInClassTrue;
    }

    public List<Long> getIdsList() {
        return idsList;
    }

    public void setIdsList(List<Long> idsList) {
        this.idsList = idsList;
    }

}
