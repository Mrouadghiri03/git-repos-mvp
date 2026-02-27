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


public class GithubRepository {

    //logique metier (calcul de date ) aussi injection de depandance ;on injecte apiservice dans repository
    private ApiService apiService;

    @Inject
    public GithubRepository(ApiService apiService) {
        this.apiService = apiService;
    }


   public void getRepositories(int page, Callback<ApiResponse> callback) {
       // 1. Calcul de la date (Aujourd'hui - 30 jours)
       Calendar calendar = Calendar.getInstance();
       calendar.add(Calendar.DAY_OF_YEAR, -30);

       // 2. Formatage au format YYYY-MM-DD

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