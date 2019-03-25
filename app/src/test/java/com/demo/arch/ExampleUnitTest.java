package com.demo.arch;

import com.orhanobut.logger.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testJust() {
        final CountDownLatch latch = new CountDownLatch(1);
        Observable.just("").subscribeOn(Schedulers.io()).observeOn(Schedulers.io()
        ).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("hello accept");
                latch.countDown();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("hello throwable");
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTimer() {
        final CountDownLatch latch = new CountDownLatch(1);
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println("long = " + aLong);
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCompletable() {

        final CountDownLatch latch = new CountDownLatch(1);
        Completable.timer(3, TimeUnit.SECONDS).subscribe(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("on running");
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDefer() {
        final CountDownLatch latch = new CountDownLatch(1);

        Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {

                return new ObservableSource<String>() {
                    @Override
                    public void subscribe(Observer<? super String> observer) {
                        observer.onNext("test1");
                        observer.onNext("test2");
                        observer.onComplete();
                    }
                };
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("accept 1");
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTimeout() {
        final CountDownLatch latch = new CountDownLatch(4);

        final Disposable disposable = Flowable.interval(2, 1, TimeUnit.SECONDS
        ).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println("along = " + aLong);
                latch.countDown();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("throwable = " + throwable);
            }
        });

        try {
            latch.await();
            if (!disposable.isDisposed()) {
                disposable.dispose();
                System.out.println("is disposed!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGUID() {
        String uuid = UUID.randomUUID().toString();
        System.out.println("uuid = " + uuid);
        uuid = UUID.randomUUID().toString();
        System.out.println("uuid = " + uuid);
    }
    @Test
    public void testListMove() {
        List<String> test = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            test.add("name" + i);
        }

        String first = test.remove(0);
        test.add(5, first);

        System.out.println(test);


    }
}