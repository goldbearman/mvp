package com.sabirovfarit.android.roompr;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    RecyclerView recyclerView;
    List<WordCheckBox> list;
    WordsListSearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.search_recycle);

        updateUI();



    }




    private void updateUI() {

        WordDataBase.getInstance().getDatabase(this).wordDao().getFlowableWordsForSearch()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<WordCheckBox>>() {
                    @Override
                    public void accept(List<WordCheckBox> wordCheckBoxes) throws Exception {
                        list = wordCheckBoxes;
                        Log.i(TAG, "accept: " + list.size());
                        updateRecycleView(list);
                    }
                });
    }

    private void updateRecycleView(List<WordCheckBox> list) {
        Log.i(TAG, "after accept: " + list.size());
        adapter = new WordsListSearchAdapter(list);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(manager);
    }
}
