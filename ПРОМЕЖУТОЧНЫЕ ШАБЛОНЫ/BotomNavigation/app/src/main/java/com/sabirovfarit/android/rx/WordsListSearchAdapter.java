package com.sabirovfarit.android.rx;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class WordsListSearchAdapter {

    List<WordCheckBox> list;

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
//            checkBox.set
        }
    }


}
