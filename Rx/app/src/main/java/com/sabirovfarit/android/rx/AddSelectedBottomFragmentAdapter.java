package com.sabirovfarit.android.rx;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class AddSelectedBottomFragmentAdapter extends RecyclerView.Adapter<AddSelectedBottomFragmentAdapter.ViewHolder> {
    private static final String TAG = "WordsListSearchAdapter";

    List<WordsList> list;
    Context context;
    List<Long> newIdsList;

    Listener listener;

    interface Listener {
        void onClick();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public AddSelectedBottomFragmentAdapter(List<WordsList> list, List<Long> newIdsList) {
        this.list = list;
        this.newIdsList = newIdsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.words_card_add_new_list, parent, false);
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
        private WordsList wordsList;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
            cardView.setOnClickListener(v -> {
                //Было два варианта:1 - позволить исплозовать слово только в одном списке или 2 - позволить добавлять слово в любой список
                //Если пользователь хочет добавить слова в несколько списков,то их надо где-то хранить
                //Решил в той же таблице words просто дублировать слова, которые выбрал пользователь

                //Получаем список id мз таблицы wordList, чтобы потом к нему добавить Id новых слов
                List<Long> listInBase = wordsList.getListId();
                Executors.newFixedThreadPool(5).execute(() -> {
                    //Получаем word по id, которые мы выбрали в checkBox
                    List<Word> wordsById = App.getInstance().getDatabase().wordDao().getWordsById(newIdsList);
                    Log.i(TAG, "ViewHolder:wordsById "+wordsById);
                    //Создаем новый список, копирую данные старых слов, чтобы убрать Id.
                    List<Word> wordsListWithoutId = new ArrayList<>();
                    for (int i = 0; i < wordsById.size(); i++) {
                        Word wordsWithoutId = new Word();
                        wordsWithoutId.setValue(wordsById.get(i).getValue());
                        wordsWithoutId.setDiffLetter(wordsById.get(i).getDiffLetter());
                        wordsWithoutId.setWordsClass(wordsById.get(i).getWordsClass());
                        wordsWithoutId.setInList(true);
                        wordsListWithoutId.add(wordsWithoutId);
                    }
                    //Вставляем новый список и получаем их id
                    List<Long> idInsertWords = App.getInstance().getDatabase().wordDao().addReturnIds(wordsListWithoutId);
                    Log.i(TAG, "ViewHolder:idInsertWords "+idInsertWords);
                    //Добавляем новые id к списку в графу listId
                    listInBase.addAll(idInsertWords);
                    Log.i(TAG, "ViewHolder:listInBase "+listInBase);
                    //Вставляем новые данные в графу listId
                    wordsList.setListId(listInBase);
                    App.getInstance().getDatabase().wordsListDao().update(wordsList);
                });

                //Закрываем фрагмент обратным вызовом
                if (listener != null) {
                    listener.onClick();
                }
            });
        }

        public void bindCard(int position) {
            wordsList = list.get(position);

            tvNameList = cardView.findViewById(R.id.tv_name_list);
            tvNumberWords = cardView.findViewById(R.id.tv_number_words);

            tvNameList.setText(wordsList.getName());

            //Show the numbers of words
            int sizeWords = wordsList.getListId().size();
            tvNumberWords.setText(context.getResources().getQuantityString(R.plurals.plurals_word, sizeWords, sizeWords));

            Drawable drawable = context.getResources().getDrawable(R.drawable.card_view_template);
            drawable.setColorFilter(wordsList.getColor(), PorterDuff.Mode.MULTIPLY);
            cardView.setBackground(drawable);
        }
    }

}
