package com.cipcipp.main.di;

import com.cipcipp.main.di.module.AppModule;
import com.cipcipp.main.ui.activitymain.ActivityMain;
import com.cipcipp.main.ui.pulseactivity.PulseActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface DiComponent {
    void inject(ActivityMain activityMain);
    void inject(PulseActivity activityMain);
}
