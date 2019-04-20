package com.example.inspect;

import android.app.Application;

import com.finotes.android.finotescore.Fn;

public class Inspect extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fn.init(this);

    }

}
