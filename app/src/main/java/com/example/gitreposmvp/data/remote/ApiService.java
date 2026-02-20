package com.example.gitreposmvp.data.remote;


import com.example.gitreposmvp.data.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search/repositories")
    Call<ApiResponse> getRepositories(

            @Query("q") String query,
            @Query("sort") String sort,
            @Query("order") String order,
            @Query("page") int page,
            @Query("per_page") int perPage
    );
}