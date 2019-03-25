package com.demo.arch.viewmodel;

import com.demo.arch.db.AppDB;
import com.demo.arch.db.entity.News;
import com.demo.arch.repository.NewsRepository;
import com.orhanobut.logger.Logger;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class NewsVM extends ViewModel implements LifecycleObserver {

    private CompositeDisposable mDisposables = new CompositeDisposable();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRefreshing = new MutableLiveData<>();

    public MutableLiveData<List<News>> newsData = new MutableLiveData<>();
    public LiveData<PagedList<News>> mPagedList;

    private PageKeyedDataSource.Factory<Integer, News> factory;
    private NewsRepository mRepository;

    public NewsVM() {
        isLoading.setValue(false);
        factory = AppDB.db().newsDao().getAllNews();
        //DataSource.Factory<Integer, News> factory = new NewsDataSourceFactory();
        mRepository = new NewsRepository();
        mPagedList = new LivePagedListBuilder<>(factory,
                new PagedList.Config.Builder()
                        .setPageSize(10)
                        .setMaxSize(25)
                        .setEnablePlaceholders(false)
                        .setPrefetchDistance(5)
                        .setInitialLoadSizeHint(20)
                        .build()
        ).setBoundaryCallback(new PagedList.BoundaryCallback<News>() {
            @Override
            public void onZeroItemsLoaded() {
                Logger.i("on zero items laoded");

                isRefreshing.setValue(true);
                Disposable disposable = Observable.just("").map(new Function<String, Boolean>() {
                    @Override
                    public Boolean apply(String s) throws Exception {

                        try {
                            final List<News> newsList = mRepository.reqAfterNewsList(0, 20);

                            AppDB.db().runInTransaction(new Runnable() {
                                @Override
                                public void run() {
                                    AppDB.db().newsDao().insertAll(newsList);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()
                ).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Logger.i("success");
                        isRefreshing.postValue(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.e("error:%s", throwable);
                        isRefreshing.postValue(false);
                    }
                });
                mDisposables.add(disposable);
            }

            @Override
            public void onItemAtFrontLoaded(@NonNull News itemAtFront) {
                Logger.i("on item at front loaded:%s",itemAtFront);
            }

            @Override
            public void onItemAtEndLoaded(@NonNull final News itemAtEnd) {
                Logger.i("on item at end loaded:%s", itemAtEnd);

                Disposable disposable = Observable.just("").map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Exception {

                        isLoading.postValue(true);
                        try {
                            final List<News> newsList = mRepository.reqAfterNewsList((int)(itemAtEnd.getId()+1), 20);

                            AppDB.db().runInTransaction(new Runnable() {
                                @Override
                                public void run() {
                                    AppDB.db().newsDao().insertAll(newsList);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        isLoading.postValue(false);

                        return null;
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()
                ).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

                mDisposables.add(disposable);

            }
        }).build();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Logger.i("on cleard in viewmodel");
        mDisposables.clear();
    }

    public void refresh() {
        Disposable disposable = Observable.just("").map(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) throws Exception {
                //清除数据库，会触发onZeroItemsLoaded, 进而触发刷新请求
                AppDB.db().newsDao().clear();

                return null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()
        ).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });

        mDisposables.add(disposable);
    }
}
