package com.hazelhunt.ninise.learnrxeasyway.part3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hazelhunt.ninise.learnrxeasyway.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * @author Ninise @ hazelhunt
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Main operator  - create
        Observable<String> someStringObserv = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext("Are you guys have any plans ");
                } catch (Error e) {
                    subscriber.onError(new Throwable("Something going wrong"));
                }

                subscriber.onCompleted();
            }
        });

        // Due to subscriber we can get data from observer
        Subscriber<String> someStringSubsr = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d("CreateObserv", "Observ completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("CreateObserv", e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.d("CreateObserv", s + "tonight?");
            }
        };

        // In this piece of code we subsribe to observer
        someStringObserv.subscribe(someStringSubsr).unsubscribe();

        Log.d("CreateObserv", "Is unsubscribed: " + someStringSubsr.isUnsubscribed());

        // It's something like subscribe on air, without declaration with using lambdas
        someStringObserv.subscribe(
                s -> Log.d("CreateObserv", s + "later?"),
                throwable -> Log.d("CreateObserv", throwable.getMessage()),
                () -> Log.d("CreateObserv", "Observ completed!"))
                .unsubscribe();


        // It's "just" operator
        Observable.just("(Just) Are you guys have any plans ")
                .subscribe(s -> Log.d("JustObserv", s + "later?"),
                        throwable -> {},
                        () -> Log.d("JustObserv", "Observ completed!"));


        // It's "from" operator
        // first of all let's create some List of days for example
        List<String> daysList = Arrays.asList(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        );

        // Lets create out Observable
        Observable.from(daysList)
                .filter(day -> !day.startsWith("T")) // Its goes without saying that,
                .subscribe(day -> Log.d("FromObserv", day)) // but this check, if day char starts with 'T'
                .unsubscribe(); // we don't want to display him.


        // It's no "defer" operator
        // Lets create some Day object
        SomeDay day = new SomeDay("Tuesday");
        // Create the Observer
        Observable<String> noDeferObserv = day.getDayObservable();

        // Lets set other value of day
        day.setDay("Monday");

        // Now have a look at our result
        noDeferObserv.subscribe(d -> Log.d("DeferObserv", d)).unsubscribe();

        // Now, we are going to do the same with defer operator
        Observable<String> deferObserv = Observable.defer(day::getDayObservable);
        day.setDay("Monday");
        deferObserv.subscribe(d -> Log.d("DeferObserv", d)).unsubscribe();
    }
}
