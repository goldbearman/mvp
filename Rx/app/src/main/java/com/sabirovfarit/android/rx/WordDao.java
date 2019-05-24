package com.sabirovfarit.android.rx;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface WordDao {

    @Insert
    void add(Word word);

    @Insert
    void add(Word... word);

    @Insert
    void add(List<Word> words);

    @Insert
    List<Long> addReturnIds(List<Word> words);

    @Update
    void update(Word word);

    @Delete
    void delete(Word word);

    @Query("UPDATE words SET inList = :inList WHERE id = :id ")
    void updateInBaceById(long id, boolean inList);

    @Query("DELETE FROM words ")
    void deleteAll();

    @Query("SELECT * FROM words ")
    List<Word> getAllWords();

    @Query("SELECT * FROM words WHERE id = :id")
    Word getWordById(Long id);

    @Query("SELECT * FROM words WHERE id IN (:ids)")
    List<Word> getWordsById(List<Long> ids);


    @Query("SELECT value,inList FROM words")
    Flowable<List<WordCheckBox>> getFlowWordsForSearch();

    @Query("SELECT * FROM words")
    Flowable<List<Word>> getWordsForSearch();


    @Query("SELECT * FROM words WHERE inList = :inList")
    LiveData<List<Word>> getAllWordsForSearchLiveData(boolean inList);
}
