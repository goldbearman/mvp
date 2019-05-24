package com.bignerdranch.android.gsoninst;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {
    @GET("/posts")
    public Call<List<Post>> getAllPosts();
}
