package com.sabirovfarit.android.rx;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sabirovfarit.android.rx.LearningWords.LearningWordsActivity;

import java.util.List;

public class WordsListLearnAdapter extends RecyclerView.Adapter<WordsListLearnAdapter.ViewHolder> {
    private static final String TAG = "WordsListSearchAdapter";

    List<WordsList> list;
    Context context;

    ListnerAllWords listnerAllWords;

    interface ListnerAllWords {
        void onClick(long idWordList);
    }

    public void setListnerAllWords(ListnerAllWords listnerAllWords) {
        this.listnerAllWords = listnerAllWords;
    }

    public WordsListLearnAdapter(List<WordsList> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.words_card_learn ,parent, false);
        this.context = parent.getContext();
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindCard(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView tvNameList;
        private TextView tvNumberWords;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

        public void bindCard(int position) {
            WordsList wordsList = list.get(position);

            tvNameList = cardView.findViewById(R.id.tv_name_list);
            tvNumberWords = cardView.findViewById(R.id.tv_number_words);

            tvNameList.setText(wordsList.getName());

            //Show the numbers of words
            int sizeWords = wordsList.getListId().size();
            tvNumberWords.setText(context.getResources().getQuantityString(R.plurals.plurals_word,sizeWords,sizeWords));

            Drawable drawable = context.getResources().getDrawable(R.drawable.card_view_template);
            drawable.setColorFilter(wordsList.getColor(), PorterDuff.Mode.MULTIPLY);
            cardView.setBackground(drawable);

            cardView.setOnClickListener(v -> {
                //Создаем listner для вызова в LearningFragment
                if (listnerAllWords != null) {
                 listnerAllWords.onClick(wordsList.getId());
                }
            });


        }
    }

}
