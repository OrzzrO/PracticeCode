package com.me.practicecode.dagger2;

import android.app.Activity;

import dagger.Component;

/**
 * Created by user on 2017/7/18.
 */
@Component(modules = PersonModule.class)
public interface PersonComponent {


    void inject(Activity activity);
}
