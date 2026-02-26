package com.example.gitreposmvp.ui;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitreposmvp.R;
import com.example.gitreposmvp.data.model.Repository;
import com.example.gitreposmvp.data.remote.ApiClient;
import com.example.gitreposmvp.data.remote.ApiService;
import com.example.gitreposmvp.data.repository.GithubRepository;
import com.example.gitreposmvp.di.AppComponent;
import com.example.gitreposmvp.di.DaggerAppComponent;
import com.example.gitreposmvp.di.NetworkModule;
import com.example.gitreposmvp.di.PresenterModule;
import com.example.gitreposmvp.presenter.MainContract;
import com.example.gitreposmvp.presenter.MainPresenter;
import com.example.gitreposmvp.ui.adapter.RepositoryAdapter;

import javax.inject.Inject;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MainContract.View {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RepositoryAdapter adapter;

    @Inject
    MainPresenter presenter;

    private int currentPage = 1;
    private static final int PAGE_SIZE = 30;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppComponent component = DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .presenterModule(new PresenterModule(this))
                .build();

        component.inject(this);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RepositoryAdapter();
        recyclerView.setAdapter(adapter);

        loadPage(currentPage);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager =
                        (LinearLayoutManager) recyclerView.getLayoutManager();

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition =
                        layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {

                        currentPage++;
                        loadPage(currentPage);
                    }
                }
            }
        });
    }

    private void loadPage(int page) {
        isLoading = true;
        presenter.loadRepositories(page);
    }

    // -------- MVP --------

    @Override
    public void showLoading() {
        if (currentPage == 1) {
            // Premier chargement : au milieu
            progressBar.setVisibility(View.VISIBLE);
        } else {
            // Chargement suivant : en bas
            adapter.showFooterLoader();
        }
    }

    @Override
    public void hideLoading() {
        isLoading = false;
        if (currentPage == 1) {
            progressBar.setVisibility(View.GONE);
        } else {
            adapter.hideFooterLoader();
        }
    }

    @Override
    public void showRepositories(List<Repository> repositories) {
        // On cache d'abord le loader si on scrollait
        if (currentPage > 1) {
            adapter.hideFooterLoader();
        }

        adapter.addData(repositories);

        if (repositories.size() < PAGE_SIZE) {
            isLastPage = true;
        }
    }

    @Override
    public void showError(String message) {
        isLoading = false;
    }
}