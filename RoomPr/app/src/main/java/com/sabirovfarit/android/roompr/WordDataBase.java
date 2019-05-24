package com.sabirovfarit.android.roompr;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class WordDataBase {

    private static final String TAG = "DataBase";

    private static WordDataBase instance;
    private AppDB database;

    public static WordDataBase getInstance() {
        if (instance == null) {
            instance = new WordDataBase();
        }
        return instance;
    }

    private WordDataBase() {
    }

    public AppDB getDatabase(Context context) {
        if (database == null) {
        database = Room.databaseBuilder(context, AppDB.class, "database")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        fillList(context);
                    }
                })
                .build();
        }
        return database;
    }

    private void fillList(final Context context) {
        Flowable.just(takeData(context))
                .observeOn(Schedulers.io())
                .subscribe(words -> {
                            Log.i(TAG, "fillList: " + words.size());
                            WordDataBase.getInstance().getDatabase(context).wordDao().add(words);
                        }
                );
    }

    private static List<Word> takeData(Context context) {

        List<Word> list = new ArrayList<>();
        try {
            InputStreamReader isr = new InputStreamReader(context.getAssets().open("words.csv"));
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
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "doInBackground: " + "end ");
        }
        return list;
    }


}
