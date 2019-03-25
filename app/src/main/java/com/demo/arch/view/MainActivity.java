package com.demo.arch.view;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.arch.R;
import com.demo.arch.db.entity.News;
import com.demo.arch.databinding.ActivityMainBinding;
import com.demo.arch.viewmodel.NewsVM;
import com.orhanobut.logger.Logger;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    NewsListAdapter mAdapter;
    NewsVM mNewsVM;
    LifecycleRegistry mRegistry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Logger.i("main activity on create");
        initNewsRv();
        initObserver();
    }

    private void initObserver() {

        mNewsVM = ViewModelProviders.of(this).get(NewsVM.class);
        mRegistry = new LifecycleRegistry(this);
        mRegistry.addObserver(mNewsVM);

        mNewsVM.newsData.observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                Logger.i("news list changed");
                mAdapter.setNewData(news);
            }
        });

        mNewsVM.isLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    mBinding.pbLoading.show();
                } else {
                    mBinding.pbLoading.hide();
                }
            }
        });


    }

    private void initNewsRv() {

        LinearLayoutManager lm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mBinding.rvNewlist.setLayoutManager(lm);
        mBinding.rvNewlist.setHasFixedSize(true);
        mAdapter = new NewsListAdapter(this);

        mBinding.rvNewlist.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
