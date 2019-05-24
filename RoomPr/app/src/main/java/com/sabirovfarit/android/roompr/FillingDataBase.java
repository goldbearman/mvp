//package com.sabirovfarit.android.roompr;
//
//import android.arch.persistence.room.Database;
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
//public class FillingDataBase {
//
//    private static final String TAG = "FillingDataBase";
//
//    private static FillingDataBase instance;
//
//    public static void initInstance(Context context) {
//
//        if (instance == null) {
//            instance = new FillingDataBase(context);
//            fillList(context);
//        }
//    }
//
//    private FillingDataBase(Context context) {
//    }
//
//    private static void fillList(final Context context) {
//        Flowable.just(takeData(context))
//                .observeOn(Schedulers.io())
//                .subscribe(words -> {
//                            Log.i(TAG, "fillList: " + words.size());
//                            WordDataBase.getInstance(context).getDatabase().wordDao().add(words);
//                        }
//                );
//    }
//
//    private static List<Word> takeData(Context context) {
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
//                list.add(word);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.i(TAG, "doInBackground: " + "end ");
//        }
//        return list;
//    }
//}
//
