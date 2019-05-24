package com.sabirovfarit.android.rx.LearningWords;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sabirovfarit.android.rx.R;
import com.sabirovfarit.android.rx.Word;

import java.util.List;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class AllWordsAdapter extends RecyclerView.Adapter<AllWordsAdapter.AllWordsHolder> {

    List<Word> list;
    Context context;

    public AllWordsAdapter(List<Word> list) {
        this.list = list;
    }

    @Override
    public AllWordsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        this.context = viewGroup.getContext();

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_words_item_layout, viewGroup, false);

        return new AllWordsHolder(view);
    }

    @Override
    public void onBindViewHolder(AllWordsHolder itemViewHolder, int position) {
        itemViewHolder.bind(position);

        //Here you can fill your row view
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class AllWordsHolder extends RecyclerView.ViewHolder {

        TextView tvValue;
        TextView tvLearningOrNot;

        public AllWordsHolder(View itemView) {
            super(itemView);
        }

        public void bind(int position) {
            Word word = list.get(position);
            tvValue = itemView.findViewById(R.id.tv_value_item);
            tvLearningOrNot = itemView.findViewById(R.id.tv_learned_or_not);
            tvValue.setText(word.getValue());
            if (word.isLearned()) {
                tvLearningOrNot.setText("Выучено");
                tvLearningOrNot.setTextColor(context.getResources().getColor(R.color.get_color_primary_dark));
            } else {
                tvLearningOrNot.setText("Не выучено");
            }

        }

    }
}
