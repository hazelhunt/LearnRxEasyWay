package com.hazelhunt.ninise.learnrxeasyway.part3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hazelhunt.ninise.learnrxeasyway.R;

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

        Subscriber<String> someStringSubsr = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d("StringObserv", "Observ completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("StringObserv", e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.d("StringObserv", s + "tonight?");
            }
        };

        someStringObserv.subscribe(someStringSubsr).unsubscribe();

        Log.d("StringObserv", "Is unsubscribed: " + someStringSubsr.isUnsubscribed());

        someStringObserv.subscribe(
                s -> Log.d("StringObserv", s + "later?"),
                throwable -> Log.d("StringObserv", throwable.getMessage()),
                () -> Log.d("StringObserv", "Observ completed!"))
                .unsubscribe();

        Observable.just("(Just) Are you guys have any plans ")
                .subscribe(s -> Log.d("StringObserv", s + "later?"),
                        null,
                        () -> Log.d("StringObserv", "Observ completed!"));
    }
}
