package com.example.forget.projectpet;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class ProjectPetApplication extends Application {
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
