package com.hfad.starbuzz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKED = "extra drinked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);


        Intent intent = getIntent();
        int drinkId = intent.getIntExtra(EXTRA_DRINKED, 0);
        Drink drink = Drink.drinks[drinkId];

        TextView name = findViewById(R.id.name);
        TextView description = findViewById(R.id.description);
        ImageView imageView = findViewById(R.id.photo);

        name.setText(drink.getName());
        description.setText(drink.getDescription());
        imageView.setImageResource(drink.getImageResourceId());
        imageView.setContentDescription(drink.getName());

    }
}
