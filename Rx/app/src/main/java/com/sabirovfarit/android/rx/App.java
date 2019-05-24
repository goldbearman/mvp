package com.sabirovfarit.android.rx;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;

import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class App extends Application {

    private static final String TAG = "Lyy";

    private static App instance;
    private AppDB database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Log.i(TAG, "onCreate: "+"under db");
        database = Room.databaseBuilder(this, AppDB.class, "database")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        fillList();
                    }
                })
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDB getDatabase() {
        return database;
    }


    private void fillList() {
        Log.i(TAG, "fillList: "+"start");
        Flowable.just(takeData())
                .observeOn(Schedulers.io())
                .subscribe(words -> {
                            Log.i(TAG, "fillList: " + words.size());
                            App.getInstance().getDatabase().wordDao().add(words);
                        }
                );

//        List<Long> lit = new ArrayList<>();
//        lit.add(5L);
//        lit.add(7L);
//
//        WordsList wordsList = new WordsList("первый", 1, lit,false);
//
//        List<WordsList> list = new ArrayList<>();
//        list.add(wordsList);
//
//
//
//        Flowable.just(takeData())
//                .filter(new Predicate<List<Word>>() {
//                    @Override
//                    public boolean test(List<Word> words) throws Exception {
//
//                        return false;
//                    }
//                })
//                .observeOn(Schedulers.io())
//                .subscribe(wordsLists -> App.getInstance().getDatabase().wordsListDao().add(wordsLists));



    }

    private List<Word> takeData() {
        Log.i(TAG, "takeData: "+"start");
        List<Word> list = new ArrayList<>();
        try {
            InputStreamReader isr = new InputStreamReader(App.this.getAssets().open("words.csv"));
            BufferedReader reader = new BufferedReader(isr);
            reader.read();
            String line;
            String[] st;
            while ((line = reader.readLine()) != null) {
                st = line.split(",");
                Word word = new Word();
                word.setValue(st[0]);
                word.setDiffLetter(Integer.parseInt(st[1]));
                word.setWordsClass(Integer.parseInt(st[2]));
                list.add(word);
                Log.i(TAG, "takeData: "+word.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "doInBackground: " + "end ");
        }
        return list;
    }
}



























