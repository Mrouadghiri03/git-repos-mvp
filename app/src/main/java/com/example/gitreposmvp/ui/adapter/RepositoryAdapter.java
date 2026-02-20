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

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {

    private List<Repository> list = new ArrayList<>();

    public void setData(List<Repository> data) {

        list = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, author, stars;
        ImageView avatar;

        public ViewHolder(View itemView) {

            super(itemView);

            name = itemView.findViewById(R.id.name);
            author = itemView.findViewById(R.id.author);
            stars = itemView.findViewById(R.id.stars);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_repository,
                                parent,
                                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Repository repo = list.get(position);

        holder.name.setText(repo.getName());

        holder.author.setText(repo.getOwner().getLogin());

        holder.stars.setText(
                "Stars: " + repo.getStars());

        Glide.with(holder.avatar.getContext())
                .load(repo.getOwner().getAvatarUrl())
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {

        return list.size();
    }
    public void addData(List<Repository> data) {

        int start = list.size();

        list.addAll(data);

        notifyItemRangeInserted(start, data.size());
    }
}