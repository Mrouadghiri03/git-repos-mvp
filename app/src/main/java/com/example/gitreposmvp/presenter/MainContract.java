package com.example.gitreposmvp.presenter;



import com.example.gitreposmvp.data.model.Repository;

import java.util.List;

public interface MainContract {

    interface View {

        void showLoading();

        void hideLoading();

        void showRepositories(List<Repository> repositories);

        void showError(String message);
    }

    interface Presenter {

        void loadRepositories(int page);
    }
}