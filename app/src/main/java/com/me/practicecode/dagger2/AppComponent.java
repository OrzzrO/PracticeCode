package com.me.practicecode.dagger2;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by user on 2017/7/18.
 */


@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    Context getContext();
}
