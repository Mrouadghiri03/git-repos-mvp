package com.example.gitreposmvp.di;

import com.example.gitreposmvp.ui.MainActivity;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {NetworkModule.class, PresenterModule.class})
public interface AppComponent {
    //rasemble les modules
    void inject(MainActivity mainActivity);
    //dagger recherche dans mainactivity , cherche la variable avec l'annotation @inject et remplis les avec les objects qui fabrique
}