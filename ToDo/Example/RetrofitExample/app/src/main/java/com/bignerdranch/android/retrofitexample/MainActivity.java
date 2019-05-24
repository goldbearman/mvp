package com.bignerdranch.android.retrofitexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkService.getInctance().
                getJSONApi().
                getAllPosts().
                enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                        Log.i("my", ""+response);

                        List<Post> posts = response.body();
                        Log.i("my", ""+posts);
//                        for (int i = 0; i < posts.size(); i++) {
//                            Log.i("my", posts.get(i).getId());
//                        }

                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {

                    }
                });



    }
}
