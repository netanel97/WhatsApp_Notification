package com.example.mobilesecurity.Utils;

import android.app.Application;

public class myApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MSPV3.initHelper(this);
    }
}
