package com.hfad.bitsandpizzas;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by fsv on 26.06.2018.
 */

public class CaptionedImageAdapter extends RecyclerView.Adapter<CaptionedImageAdapter.ViewHolder> {

    private String[] captions;
    private int[] imageIds;

    Listener listener;

    interface Listener {
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public CaptionedImageAdapter(String[] captions, int[] imageIds) {
        this.captions = captions;
        this.imageIds = imageIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image, parent, false);

        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView image = holder.cardView.findViewById(R.id.info_image);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        image.setImageDrawable(drawable);
        image.setContentDescription(captions[position]);

        TextView name = holder.cardView.findViewById(R.id.info_text);
        name.setText(captions[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /////  ПРИ НАЖАТИИ ВЫЗЫВАЕТСЯ МЕТОД ЛИСТЕНЕР САМОЙ АКТИВНОСТИ  ////
                if (listener != null) {
                listener.onClick(position);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return captions.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
