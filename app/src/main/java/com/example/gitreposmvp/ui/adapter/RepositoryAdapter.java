package com.example.gitreposmvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gitreposmvp.R;
import com.example.gitreposmvp.data.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_LOADING = 1;

    private List<Repository> list = new ArrayList<>();
    private boolean isLoaderVisible = false;

    // ViewHolder pour les dépôts
    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        TextView name, author, stars, description;
        ImageView avatar;

        public RepoViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            author = itemView.findViewById(R.id.author);
            stars = itemView.findViewById(R.id.stars);
            description = itemView.findViewById(R.id.description);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }

    // ViewHolder li kayn lT7T
    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Si c'est le dernier élément et que le loader doit être visible
        if (isLoaderVisible && position == list.size() - 1) {
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
            return new RepoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RepoViewHolder) {
            Repository repo = list.get(position);
            RepoViewHolder repoHolder = (RepoViewHolder) holder;

            repoHolder.name.setText(repo.getName());
            repoHolder.author.setText(repo.getOwner().getLogin());
            repoHolder.stars.setText("⭐ " + repo.getStars());

            // Gestion de la description (vérifie si elle est nulle)
            if (repo.getDescription() != null) {
                repoHolder.description.setText(repo.getDescription());
                repoHolder.description.setVisibility(View.VISIBLE);
            } else {
                repoHolder.description.setVisibility(View.GONE);
            }

            Glide.with(repoHolder.avatar.getContext())
                    .load(repo.getOwner().getAvatarUrl())
                    .circleCrop()
                    .into(repoHolder.avatar);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<Repository> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    // --- Méthodes pour gérer le Loader de bas de page ---
    public void showFooterLoader() {
        if (!isLoaderVisible) {
            isLoaderVisible = true;
            list.add(null); // On ajoute un élément vide pour déclencher TYPE_LOADING
            notifyItemInserted(list.size() - 1);
        }
    }

    public void hideFooterLoader() {
        if (isLoaderVisible && list.size() > 0) {
            isLoaderVisible = false;
            int lastIndex = list.size() - 1;
            if (list.get(lastIndex) == null) {
                list.remove(lastIndex);
                notifyItemRemoved(lastIndex);
            }
        }
    }
}