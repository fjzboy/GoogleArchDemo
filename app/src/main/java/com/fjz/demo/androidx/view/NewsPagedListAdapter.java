package com.fjz.demo.androidx.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fjz.demo.androidx.R;
import com.fjz.demo.androidx.databinding.NewsItemBinding;
import com.fjz.demo.androidx.db.entity.News;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class NewsPagedListAdapter extends PagedListAdapter<News, NewsPagedListAdapter.ViewHolder> {

    protected NewsPagedListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NewsPagedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.news_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsPagedListAdapter.ViewHolder holder, int position) {
        holder.binding.setNews(getItem(position));
        holder.binding.executePendingBindings();
    }

    public static final DiffUtil.ItemCallback<News> DIFF_CALLBACK = new DiffUtil.ItemCallback<News>() {
        @Override
        public boolean areItemsTheSame(@NonNull News oldItem, @NonNull News newItem) {

            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull News oldItem, @NonNull News newItem) {

            return (oldItem.getId() == newItem.getId()) &&
                    (oldItem.getTitle().equals(newItem.getTitle()));
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {

        NewsItemBinding binding;

        ViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
