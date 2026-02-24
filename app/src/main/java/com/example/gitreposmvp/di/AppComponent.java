package com.example.gitreposmvp.di;

import com.example.gitreposmvp.ui.MainActivity;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {NetworkModule.class, PresenterModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
}