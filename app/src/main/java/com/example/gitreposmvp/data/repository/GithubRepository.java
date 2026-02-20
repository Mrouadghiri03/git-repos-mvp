package com.example.gitreposmvp.data.repository;


import com.example.gitreposmvp.data.model.ApiResponse;
import com.example.gitreposmvp.data.remote.ApiService;

import retrofit2.Call;
import retrofit2.Callback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class GithubRepository {

    private ApiService apiService;

    public GithubRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getRepositories(
            int page,
            Callback<ApiResponse> callback
    ) {
        // 1. Obtenir la date d'il y a 30 jours
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -30);

        // 2. Formater la date en "yyyy-MM-dd"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String formattedDate = sdf.format(calendar.getTime());

        // 3. Construire la requête GitHub (ex: created:>2026-01-21)
        String query = "created:>" + formattedDate;
        Call<ApiResponse> call =
                apiService.getRepositories(
                        query,
                        "stars",
                        "desc",
                        page,
                        30
                );

        call.enqueue(callback);
    }
}