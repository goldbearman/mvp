package com.sabirovfarit.android.roompr;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
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

    @Update
    void update(Word word);

    @Delete
    void delete(Word word);

    @Query("DELETE FROM words ")
    void deleteAll();

    @Query("SELECT * FROM words ")
    List<Word> getAllWords();

    @Query("SELECT * FROM words WHERE id = :id")
    Word getWordById(Long id);

    @Query("SELECT value,inList FROM words")
    List<WordCheckBox> getWordsForSearch();

    @Query("SELECT value,inList FROM words")
    Flowable<List<WordCheckBox>> getFlowableWordsForSearch();
}
