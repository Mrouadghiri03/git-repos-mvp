package com.example.gitreposmvp.ui;



import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitreposmvp.R;
import com.example.gitreposmvp.data.model.Repository;
import com.example.gitreposmvp.data.remote.ApiClient;
import com.example.gitreposmvp.data.remote.ApiService;
import com.example.gitreposmvp.data.repository.GithubRepository;
import com.example.gitreposmvp.presenter.MainContract;
import com.example.gitreposmvp.presenter.MainPresenter;
import com.example.gitreposmvp.ui.adapter.RepositoryAdapter;

import java.util.List;

/*
public class MainActivity extends AppCompatActivity implements MainContract.View {

    private ProgressBar progressBar;

    private RepositoryAdapter adapter;

    private MainPresenter presenter;

    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        RecyclerView recyclerView =
                findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        adapter = new RepositoryAdapter();

        recyclerView.setAdapter(adapter);

        ApiService apiService =
                ApiClient.getRetrofit()
                        .create(ApiService.class);

        GithubRepository repository =
                new GithubRepository(apiService);

        presenter =
                new MainPresenter(this,
                        repository);

        presenter.loadRepositories(1);
    }

    @Override
    public void showLoading() {

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRepositories(List<Repository> repositories) {

        adapter.setData(repositories);
    }

    @Override
    public void showError(String message) {

    }
}
*/
public class MainActivity extends AppCompatActivity implements MainContract.View {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RepositoryAdapter adapter;
    private MainPresenter presenter;

    // Pagination variables
    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_SIZE = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new RepositoryAdapter();
        recyclerView.setAdapter(adapter);

        // Init Retrofit
        ApiService apiService =
                ApiClient.getRetrofit()
                        .create(ApiService.class);

        GithubRepository repository =
                new GithubRepository(apiService);

        presenter =
                new MainPresenter(this, repository);

        // Load first page
        loadPage(1);

        // Scroll listener
        recyclerView.addOnScrollListener(
                new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrolled(
                            RecyclerView recyclerView,
                            int dx,
                            int dy) {

                        super.onScrolled(recyclerView, dx, dy);

                        if (dy <= 0) return;

                        int visibleItemCount =
                                layoutManager.getChildCount();

                        int totalItemCount =
                                layoutManager.getItemCount();

                        int firstVisibleItemPosition =
                                layoutManager
                                        .findFirstVisibleItemPosition();

                        if (!isLoading && !isLastPage) {

                            if ((visibleItemCount
                                    + firstVisibleItemPosition)
                                    >= totalItemCount
                                    && firstVisibleItemPosition >= 0) {

                                currentPage++;
                                loadPage(currentPage);
                            }
                        }
                    }
                });
    }

    private void loadPage(int page) {

        if (isLoading || isLastPage) return;

        isLoading = true;
        presenter.loadRepositories(page);
    }

    // -------------------------
    // MVP View methods
    // -------------------------

    @Override
    public void showLoading() {

        if (currentPage == 1)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRepositories(List<Repository> repositories) {

        if (currentPage == 1) {
            adapter.setData(repositories);
        } else {
            adapter.addData(repositories);
        }

        if (repositories.size() < PAGE_SIZE) {
            isLastPage = true;
        }

        isLoading = false;
    }

    @Override
    public void showError(String message) {

        isLoading = false;
    }
}