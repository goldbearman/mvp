package com.hfad.bitsandpizzas;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = findViewById(R.id.toolbarN);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    public void onClickDone(View view) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.coordin), "Your order has been updated", Snackbar.LENGTH_SHORT);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OrderActivity.this,"Undone!",Toast.LENGTH_SHORT).show();
            }
        });
        snackbar.show();
    }
}
