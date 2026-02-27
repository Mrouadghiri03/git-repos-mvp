package com.example.gitreposmvp.presenter;


import com.example.gitreposmvp.data.model.ApiResponse;
import com.example.gitreposmvp.data.model.Repository;
import com.example.gitreposmvp.data.repository.GithubRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;


public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private GithubRepository repository;
    //class de logique metier fait le pont entre les données et laffichage
    @Inject
    public MainPresenter(MainContract.View view,
                         GithubRepository repository) {
        this.view = view;
        this.repository = repository;
    }
     public  void attachView(MainContract.View view){
        this.view = view;
     }
    @Override
    public void loadRepositories(int page) {

        view.showLoading();

        repository.getRepositories(page, new Callback<ApiResponse>() {

            @Override
            public void onResponse(Call<ApiResponse> call,
                                   Response<ApiResponse> response) {

                view.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    // Récupère la liste des repositories depuis ApiResponse
                    List<Repository> repos = response.body().getItems();

                    view.showRepositories(repos);

                } else {
                    view.showError("Erreur serveur");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                view.hideLoading();
                view.showError(t.getMessage());
            }
        });
    }
}