package com.example.inspect;

import android.app.Application;
import android.content.Context;

import com.finotes.android.finotescore.Fn;

public class Inspect extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fn.init(this);
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTION", "Inspection");

    }

}
