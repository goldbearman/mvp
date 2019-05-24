package com.sabirovfarit.android.roompr;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class WordsListSearchAdapter extends RecyclerView.Adapter<WordsListSearchAdapter.ViewHolder> {
    private static final String TAG = "WordsListSearchAdapter";

    List<WordCheckBox> list;

    public WordsListSearchAdapter(List<WordCheckBox> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.words_card_search,parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindCard(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView textView;
        private CheckBox checkBox;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

        public void bindCard(WordCheckBox wordCheckBox) {
            textView = cardView.findViewById(R.id.search_textView);
            checkBox = cardView.findViewById(R.id.search_checkBox);

            textView.setText(wordCheckBox.getValue());
            checkBox.setChecked(wordCheckBox.isInList());

        }
    }
}
