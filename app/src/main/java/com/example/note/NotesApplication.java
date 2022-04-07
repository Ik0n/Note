package com.example.note;

import android.app.Application;

public class NotesApplication extends Application {

    private static NotesApplication instance;

    public static NotesApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
