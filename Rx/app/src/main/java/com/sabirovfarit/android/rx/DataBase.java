//package com.sabirovfarit.android.rx;
//
//import android.arch.persistence.room.Room;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import io.reactivex.Flowable;
//import io.reactivex.schedulers.Schedulers;
//
//public class DataBase {
//
//    private static final String TAG = "DataBase";
//
//    private static DataBase instance;
//    private static AppDB database;
//
//    public static DataBase getInstance(Context context) {
//        if (instance == null) {
//            instance = new DataBase(context);
//        }
//        return instance;
//    }
//
//    private DataBase(Context context) {
//        if (database == null) {
//            database = Room.databaseBuilder(context, AppDB.class, "database")
//                    .build();
//            fillList(context);
//        }
//    }
//
//    public AppDB getDatabase(Context context) {
//        return database;
//    }
//
//    private void fillList(Context context) {
//
////        Flowable.just(takeData(context))
////                .observeOn(Schedulers.io())
////                .subscribe(words -> {
////                            Log.i(TAG, "fillList: " + words.size());
////                            database.wordDao().add(words);
////                        }
////                );
//
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                database.wordDao().add(takeData(context));
//            }
//        });
//    }
//
//    private List<Word> takeData(Context context) {
//
//        List<Word> list = new ArrayList<>();
//        try {
//            InputStreamReader isr = new InputStreamReader(context.getAssets().open("words.csv"));
//            BufferedReader reader = new BufferedReader(isr);
//            reader.read();
//            String line;
//            String[] st;
//            while ((line = reader.readLine()) != null) {
//                st = line.split(",");
//                Word word = new Word();
//                word.setValue(st[0]);
//                word.setDiffLetter(Integer.parseInt(st[1]));
//                word.setWordsClass(Integer.parseInt(st[2]));
//
//                list.add(word);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.i(TAG, "doInBackground: " + "end ");
//        }
//
//        return list;
//    }
//
//
//}
