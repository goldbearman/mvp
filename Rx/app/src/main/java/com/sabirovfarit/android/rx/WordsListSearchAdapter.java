package com.sabirovfarit.android.rx;

import android.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import java.util.ArrayList;
import java.util.List;


import java.util.zip.Inflater;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class WordsListSearchAdapter extends RecyclerView.Adapter<WordsListSearchAdapter.ViewHolder> {
    private static final String TAG = "WordsListSearchAdapter";

    private List<Word> list;
    //Create for search
    private List<Word> filteredList = new ArrayList<>();
    private String query;

    public WordsListSearchAdapter(List<Word> list) {
        this.list = list;
        setQuery("");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.words_card_search, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindCard(position);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView textView;
        private CheckBox checkBox;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

        public void bindCard(int position) {
            Word word = filteredList.get(position);

            textView = cardView.findViewById(R.id.search_textView);
            checkBox = cardView.findViewById(R.id.search_checkBox);

            textView.setText(word.getValue());

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                word.setInList(isChecked);

            });

            checkBox.setChecked(word.isInList());
        }
    }

    public List<Word> getList() {
        return list;
    }

    public void setList(List<Word> list) {
        this.list = list;
        setQuery("");
    }
    //All checkBox assign true or false
    public void selectCheckBoxBoolean(Boolean b) {
        for (Word word : list) {
            word.setInList(b);
        }
    }

    private void filterList() {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(list);
        } else {
            for (Word word : list) {
                if (word.getValue().toLowerCase().contains(query)) {
                    filteredList.add(word);
                }
            }
        }
    }

    public void setQuery(String query) {
        this.query = query;
        filterList();
    }


}
