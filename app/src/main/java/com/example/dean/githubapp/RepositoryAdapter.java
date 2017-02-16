package com.example.dean.githubapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dean.githubapp.data.Repository;
import com.example.dean.githubapp.utilities.ImageUtils;
import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private static final String LOG_TAG = RepositoryAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    private List<Repository> mRepositoryList;

    public RepositoryAdapter(List<Repository> repositoryList, ListItemClickListener listener) {
        mRepositoryList = repositoryList;
        mOnClickListener = listener;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repository_list_item, parent, false);

        return new RepositoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        new ImageUtils.DownloadImageAsyncTask(holder.avatarImageView)
                .execute(mRepositoryList.get(position).getAvatarUrl());

        holder.repoNameTextView.setText(mRepositoryList.get(position).getName());
        holder.authorNameTextView.setText(mRepositoryList.get(position).getOwner());
        holder.watchersTextView.setText(String.valueOf(mRepositoryList.get(position).getWatchers()));
        holder.forksTextView.setText(String.valueOf(mRepositoryList.get(position).getForks()));
        holder.issuesTextView.setText(String.valueOf(mRepositoryList.get(position).getIssues()));
    }

    @Override
    public int getItemCount() {
        return mRepositoryList.size();
    }

    class RepositoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView avatarImageView;
        TextView repoNameTextView;
        TextView authorNameTextView;
        TextView watchersTextView;
        TextView forksTextView;
        TextView issuesTextView;

        public RepositoryViewHolder(View itemView) {
            super(itemView);

            avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
            repoNameTextView = (TextView) itemView.findViewById(R.id.tv_repo_name);
            authorNameTextView = (TextView) itemView.findViewById(R.id.tv_author_name);
            watchersTextView = (TextView) itemView.findViewById(R.id.tv_watchers);
            forksTextView = (TextView) itemView.findViewById(R.id.tv_forks);
            issuesTextView = (TextView) itemView.findViewById(R.id.tv_issues);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClicked(clickedPosition);
        }
    }

    public interface ListItemClickListener {
        void onListItemClicked(int clickedItemIndex);
    }
}
