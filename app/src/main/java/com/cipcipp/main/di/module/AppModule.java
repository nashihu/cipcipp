package com.cipcipp.main.di.module;

import android.app.Application;
import android.content.Context;

import com.cipcipp.main.engine.DatabaseHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application application;
    public AppModule(Application application) {this.application = application;}

    @Provides
    @Singleton
    Context provideAppContext() {return application.getApplicationContext();}

    @Provides
    @Singleton
    DatabaseHandler provideDatabaseHandler() {return new DatabaseHandler(application.getApplicationContext());}
}
