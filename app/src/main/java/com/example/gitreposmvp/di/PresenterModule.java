package com.example.gitreposmvp.di;

import com.example.gitreposmvp.data.model.Repository;
import com.example.gitreposmvp.data.remote.ApiService;
import com.example.gitreposmvp.data.repository.GithubRepository;
import com.example.gitreposmvp.presenter.MainContract;
import com.example.gitreposmvp.presenter.MainPresenter;
import dagger.Module;
import dagger.Provides;
import retrofit2.Call;
import retrofit2.Callback;

import javax.inject.Inject;
import java.util.List;


@Module
public class PresenterModule {

    private final MainContract.View view;

    public PresenterModule(MainContract.View view) {
        this.view = view;
    }

    @Provides
    MainContract.View provideView() {
        return view;
    }

    @Provides
    MainPresenter providePresenter(GithubRepository repository,
                                   MainContract.View view) {
        return new MainPresenter(view, repository);
    }
}


