package com.cipcipp.main.di;

import android.app.Application;

import com.cipcipp.main.di.module.AppModule;

public class MainApp extends Application {
    DiComponent component;
    public DiComponent getComponent() {return component;}

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerDiComponent.builder().appModule(new AppModule(this)).build();
    }
}
