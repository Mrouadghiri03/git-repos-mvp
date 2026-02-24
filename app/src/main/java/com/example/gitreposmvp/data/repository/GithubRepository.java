package com.example.gitreposmvp.data.repository;


import com.example.gitreposmvp.data.model.ApiResponse;
import com.example.gitreposmvp.data.model.Repository;
import com.example.gitreposmvp.data.remote.ApiService;

import retrofit2.Call;
import retrofit2.Callback;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
/*
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

 */


import com.example.gitreposmvp.data.model.ApiResponse;
import com.example.gitreposmvp.data.remote.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;

public class GithubRepository {

    private ApiService apiService;

    @Inject
    public GithubRepository(ApiService apiService) {
        this.apiService = apiService;
    }

   /* public void getRepositories(int page,
                                Callback<ApiResponse> callback) {

        Call<ApiResponse> call =
                apiService.getRepositories(
                        "created:>2024-01-01",
                        "stars",
                        "desc",
                        page,
                        30
                );

        call.enqueue(callback);
    }

    */
   public void getRepositories(int page, Callback<ApiResponse> callback) {
       // 1. Calcul de la date (Aujourd'hui - 30 jours)
       Calendar calendar = Calendar.getInstance();
       calendar.add(Calendar.DAY_OF_YEAR, -30);

       // 2. Formatage au format YYYY-MM-DD
       // On utilise Locale.US pour garantir que les chiffres soient 0-9
       // peu importe la langue du téléphone de l'utilisateur.
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
       String formattedDate = sdf.format(calendar.getTime());

       // 3. Construction de la requête dynamique
       String query = "created:>" + formattedDate;

       // 4. Appel API Retrofit (Legacy Java style)
       Call<ApiResponse> call = apiService.getRepositories(
               query,
               "stars",
               "desc",
               page,
               30
       );

       call.enqueue(callback);
   }
}