package com.hfad.starbuzz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DrinkCategoryActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);

        ArrayAdapter<Drink> arrayAdapter = new ArrayAdapter<Drink>(this, android.R.layout.simple_list_item_1, Drink.drinks);
        ListView listView = findViewById(R.id.list_drinks);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(onItemClickListener);
    }


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            ///////////////////// ПЕРЕДАЕМ ID  /////////////////////
            Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
            intent.putExtra(DrinkActivity.EXTRA_DRINKED , (int) id);
            startActivity(intent);

        }
    };
}
