package com.demo.arch.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.demo.arch.R;
import com.demo.arch.databinding.ActivityPagedNewsListBinding;
import com.demo.arch.db.entity.News;
import com.demo.arch.viewmodel.NewsVM;
import com.orhanobut.logger.Logger;


public class PagedNewsListActivity extends AppCompatActivity {

    ActivityPagedNewsListBinding binding;
    PagedNewsListAdapter mAdapter;
    NewsVM mVM;

    LifecycleRegistry registry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_paged_news_list);
        initViews();

        mVM = ViewModelProviders.of(this).get(NewsVM.class);

        mVM.mPagedList.observe(this, new Observer<PagedList<News>>() {
            @Override
            public void onChanged(PagedList<News> news) {
                mAdapter.submitList(news);
            }
        });
        mVM.isLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.pbLoading.show();
                } else {
                    binding.pbLoading.hide();
                }
            }
        });

        mVM.isRefreshing.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Logger.i("on refreshing:%b", aBoolean);
                binding.swpRefreshLayout.setRefreshing(aBoolean);
            }
        });

        registry = new LifecycleRegistry(this);
        registry.addObserver(mVM);

    }

    private void initViews() {

        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvNewsList.setLayoutManager(llm);
        binding.rvNewsList.setHasFixedSize(true);

        mAdapter = new PagedNewsListAdapter(this, PagedNewsListAdapter.diffCallback);

        binding.rvNewsList.setAdapter(mAdapter);


        binding.swpRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mVM.refresh();
            }
        });
    }
}
