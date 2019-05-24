package com.hfad.bitsandpizzas;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class PizzaDetailActivity extends AppCompatActivity {


    public static final String PIZZA_ID = "pizzaId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_p);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView textView = findViewById(R.id.pizza_text);
        ImageView imageView = findViewById(R.id.pizza_image);

        int pizzaId = (Integer)getIntent().getExtras().get(PIZZA_ID);
//        pizzaId = 0;


        String pizzaName = Pizza.pizza[pizzaId].getName();
        textView.setText(pizzaName);
        int pizzaImage = Pizza.pizza[pizzaId].getImageResourceId();
        imageView.setImageDrawable(ContextCompat.getDrawable(this,pizzaImage));
        imageView.setContentDescription(pizzaName);
    }
}
