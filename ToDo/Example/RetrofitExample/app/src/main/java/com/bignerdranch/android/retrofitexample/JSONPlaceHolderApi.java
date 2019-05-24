package com.bignerdranch.android.retrofitexample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceHolderApi {
    @GET("https://api.github.com/repos/square/retrofit/contributors")
    public Call<List<Post>> getAllPosts();
}
