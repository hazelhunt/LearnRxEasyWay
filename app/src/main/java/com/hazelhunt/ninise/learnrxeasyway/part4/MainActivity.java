package com.hazelhunt.ninise.learnrxeasyway.part4;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> someList = Arrays.asList("Apples", "Oranges", "Cucumber", "Potato", "Gamburger", "Hot dog");

        // Buffer operator
        Observable.from(someList)
                .buffer(2)
                .subscribe(strings -> Log.d("buffer", "list: " + strings));

        //     Output
        //     04-04 13:57:05.372 3770-3770/com.hazelhunt.ninise.learnrxeasyway D/buffer: list: [Apples, Oranges]
        //     04-04 13:57:05.372 3770-3770/com.hazelhunt.ninise.learnrxeasyway D/buffer: list: [Cucumber, Potato]
        //     04-04 13:57:05.372 3770-3770/com.hazelhunt.ninise.learnrxeasyway D/buffer: list: [Gamburger, Hot dog]


        // Flatmap operator
        Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                subscriber.onNext(someList);
                subscriber.onCompleted();
            }
        }).flatMap(new Func1<List<String>, Observable<?>>() {
            @Override
            public Observable<?> call(List<String> strings) {
                return Observable.from(strings);
            }
        }).filter(item -> !item.equals("Gamburger") && !item.equals("Hot dog"))
                .subscribe(string -> Log.d("flatmap", "item: " + string));

        //     Output
        //     04-04 14:15:12.524 19953-19953/com.hazelhunt.ninise.learnrxeasyway D/flatmap: item: Apples
        //     04-04 14:15:12.524 19953-19953/com.hazelhunt.ninise.learnrxeasyway D/flatmap: item: Oranges
        //     04-04 14:15:12.524 19953-19953/com.hazelhunt.ninise.learnrxeasyway D/flatmap: item: Cucumber
        //     04-04 14:15:12.524 19953-19953/com.hazelhunt.ninise.learnrxeasyway D/flatmap: item: Potato

        // With lambda
        Observable.just(someList)
                .flatMap(Observable::from)
                .filter(item -> !item.equals("Gamburger") && !item.equals("Hot dog"))
                .subscribe(string -> Log.d("flatmap lambda", "item: " + string));

        //     Output
        //     04-04 14:15:12.524 19953-19953/com.hazelhunt.ninise.learnrxeasyway D/flatmap lambda: item: Apples
        //     04-04 14:15:12.524 19953-19953/com.hazelhunt.ninise.learnrxeasyway D/flatmap lambda: item: Oranges
        //     04-04 14:15:12.524 19953-19953/com.hazelhunt.ninise.learnrxeasyway D/flatmap lambda: item: Cucumber
        //     04-04 14:15:12.524 19953-19953/com.hazelhunt.ninise.learnrxeasyway D/flatmap lambda: item: Potato

    }
}
