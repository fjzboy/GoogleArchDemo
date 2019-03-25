package com.fjz.demo.androidx.view;

import android.content.Context;
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

public class PagedNewsListAdapter extends PagedListAdapter<News, PagedNewsListAdapter.ViewHolder> {

    LayoutInflater inflater;

    protected PagedNewsListAdapter(Context context, @NonNull DiffUtil.ItemCallback<News> diffCallback) {
        super(diffCallback);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        NewsItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.news_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        NewsItemBinding binding;

        public ViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(News news) {
            binding.setNews(news);
            binding.executePendingBindings();
        }
    }


    public static final DiffUtil.ItemCallback<News> diffCallback = new DiffUtil.ItemCallback<News>() {
        @Override
        public boolean areItemsTheSame(@NonNull News oldItem, @NonNull News newItem) {

            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull News oldItem, @NonNull News newItem) {

            return (oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getAuthor().equals(newItem.getAuthor()) &&
                    oldItem.getSubTitle().equals(newItem.getSubTitle()));
        }
    };
}
