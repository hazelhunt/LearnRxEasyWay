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
import rx.observables.GroupedObservable;

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


        // GroupBy
        Observable.from(Arrays.asList(1,2,3,4,5,6,7,8,9,10)).groupBy(integer -> integer % 2 == 0)
                .subscribe(value -> value
                        .toList()
                        .subscribe(num -> Log.d("groupby", "value: " + num)))
                .unsubscribe();

        //      Output
        //      04-06 14:23:21.787 7018-7018/com.hazelhunt.ninise.learnrxeasyway D/groupby: value: [1, 3, 5, 7, 9]
        //      04-06 14:23:21.787 7018-7018/com.hazelhunt.ninise.learnrxeasyway D/groupby: value: [2, 4, 6, 8, 10]

        // Map
        StringBuilder builder = new StringBuilder("Hello ");
        Observable.just(builder)
                .map(new Func1<StringBuilder, StringBuilder>() {
                    @Override
                    public StringBuilder call(StringBuilder stringBuilder) {
                        return stringBuilder.append("there");
                    }
                })
                .map(new Func1<StringBuilder, StringBuilder>() {
                    @Override
                    public StringBuilder call(StringBuilder stringBuilder) {
                        return stringBuilder.append("!");
                    }
                })
                .subscribe(stringBuilder -> Log.d("map", "string: " + stringBuilder.toString()))
                .unsubscribe();

        //      Output
        //      04-06 14:42:56.087 23819-23819/com.hazelhunt.ninise.learnrxeasyway D/map: string: Hello there!

        // With lambda
        Observable.just(builder)
                .map(s -> s.append(" My name is"))
                .map(s -> s.append(" "))
                .map(s -> s.append("Ninise"))
                .subscribe(stringBuilder -> Log.d("map", "string: " + stringBuilder.toString()))
                .unsubscribe();

        //      Output
        //      04-06 14:42:56.087 23819-23819/com.hazelhunt.ninise.learnrxeasyway D/map: string: Hello there! My name is Ninise


        // Scan
        Observable.just(1,2,3,4,5,6)
                .scan((x, y) -> x + y)
                .subscribe(
                        integer -> Log.d("scan", "number: " + integer)
                ).unsubscribe();

        //      Output
        //      04-07 15:27:26.330 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/scan: number: 1
        //      04-07 15:27:26.330 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/scan: number: 3
        //      04-07 15:27:26.330 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/scan: number: 6
        //      04-07 15:27:26.330 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/scan: number: 10
        //      04-07 15:27:26.330 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/scan: number: 15
        //      04-07 15:27:26.330 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/scan: number: 21

        // Window
        final int[] count = {0};
        Observable.just(1,2,3,4,5)
                .window(2)
                .subscribe(integerObservable ->
                    integerObservable.subscribe(
                            integer -> Log.d("window", "number: " + integer),
                            (e) -> {},
                            () -> Log.d("window", "onCompleted " + count[0]++)
                    ),
                        (e) -> {},
                        () -> Log.d("window", "Completed"))
                .unsubscribe();

        //      Output
        //      04-07 15:33:33.470 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/window: number: 1
        //      04-07 15:33:33.470 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/window: number: 2
        //      04-07 15:33:33.470 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/window: onCompleted 0
        //      04-07 15:33:33.470 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/window: number: 3
        //      04-07 15:33:33.470 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/window: number: 4
        //      04-07 15:33:33.470 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/window: onCompleted 1
        //      04-07 15:33:33.470 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/window: number: 5
        //      04-07 15:33:33.470 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/window: onCompleted 2
        //      04-07 15:33:33.470 32010-32010/com.hazelhunt.ninise.learnrxeasyway D/window: Completed


    }
}
