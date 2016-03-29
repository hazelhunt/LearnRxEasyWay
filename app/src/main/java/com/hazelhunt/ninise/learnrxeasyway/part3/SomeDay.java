package com.hazelhunt.ninise.learnrxeasyway.part3;

import rx.Observable;

public class SomeDay {

    private String day;

    public SomeDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Observable<String> getDayObservable() {
        return Observable.just(day);
    }
}
