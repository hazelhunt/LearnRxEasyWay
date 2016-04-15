package com.hazelhunt.ninise.learnrxeasyway.part5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // First
        Observable.from(Arrays.asList(1,2,3,4,5,5,6,7,8))
                .first()
                .subscribe(integer -> Log.d("first", "num: " + integer));

        // output
        // 04-15 13:41:38.301 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/first: num: 1

        // Debounce
        Observable.from(Arrays.asList(1,2,3,4,5,6,7))
                .debounce(350, TimeUnit.MILLISECONDS)
                .subscribe(integer -> Log.d("debounce", "num: " + integer));

        // output
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/debounce: num: 7

        // distinct
        Observable.from(Arrays.asList(1,2,2,2,4,5,5,3))
                .distinct()
                .subscribe(integer -> Log.d("distinct", "num: " + integer));

        // output
        //  04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/distinct: num: 1
        //  04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/distinct: num: 2
        //  04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/distinct: num: 4
        //  04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/distinct: num: 5
        //  04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/distinct: num: 3

        // elementAt
        Observable.from(Arrays.asList(1,2,3,4,5,6,7,8))
                .elementAt(3)
                .subscribe(integer -> Log.d("elementAt", "num: " + integer));

        // output
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/elementAt: num: 4

        // filter
        Observable.from(Arrays.asList(1,2,3,4,5,6,7,8))
                .filter(i -> i > 4)
                .subscribe(integer -> Log.d("filter", "num: " + integer));

        // output
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/filter: num: 5
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/filter: num: 6
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/filter: num: 7
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/filter: num: 8

        // ignoreElements
        Observable.from(Arrays.asList(1,2,3,4,5,6,7,8,9))
                .ignoreElements()
                .subscribe(integer -> Log.d("ignoreElements", "num: " + integer));

        // last
        Observable.from(Arrays.asList(1,2,3,4,5,6))
                .last()
                .subscribe(integer -> Log.d("last", "num: " + integer));

        // output
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/last: num: 6

        // sample
        Observable.from(Arrays.asList(1,2,3,4,5,6,7))
                .sample(10, TimeUnit.MILLISECONDS)
                .subscribe(integer -> Log.d("sample", "num: " + integer));

        // skip
        Observable.from(Arrays.asList(1,2,3,4,5,6,7,8))
                .skip(4)
                .subscribe(integer -> Log.d("skip", "num: " + integer));

        // output
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/skip: num: 5
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/skip: num: 6
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/skip: num: 7
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/skip: num: 8

        // skipLast
        Observable.from(Arrays.asList(1,2,3,4,5,6,7,8))
                .skipLast(4)
                .subscribe(integer -> Log.d("skipLast", "num: " + integer));

        // output
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/skipLast: num: 1
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/skipLast: num: 2
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/skipLast: num: 3
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/skipLast: num: 4

        // take
        Observable.from(Arrays.asList(1,2,3,4,5,6,7,8))
                .take(2)
                .subscribe(integer -> Log.d("take", "num: " + integer));

        // output
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/take: num: 1
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/take: num: 2

        // takeLast
        Observable.from(Arrays.asList(1,2,3,4,5,6,7,8))
                .takeLast(2)
                .subscribe(integer -> Log.d("takeLast", "num: " + integer));

        // output
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/takeLast: num: 7
        // 04-15 13:41:38.305 2728-2728/com.hazelhunt.ninise.learnrxeasyway D/takeLast: num: 8
    }
}
