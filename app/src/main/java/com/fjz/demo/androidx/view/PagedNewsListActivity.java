package com.fjz.demo.androidx.view;

import android.os.Bundle;

import com.fjz.demo.androidx.R;
import com.fjz.demo.androidx.databinding.ActivityPagedNewsListBinding;
import com.fjz.demo.androidx.db.entity.News;
import com.fjz.demo.androidx.viewmodel.NewsVM;
import com.orhanobut.logger.Logger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


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
