package com.example.gitreposmvp.presenter;



import com.example.gitreposmvp.data.model.Repository;

import java.util.List;

public interface MainContract {
    //package presenter , intermidaire entre la vue et le model , il gere la logique
    interface View {
        // ce que UI peut faire (chargement , erreur ................)
        void showLoading();

        void hideLoading();

        void showRepositories(List<Repository> repositories);

        void showError(String message);
    }

    interface Presenter {
        //les actions que user peut faire
        void loadRepositories(int page);
    }
}