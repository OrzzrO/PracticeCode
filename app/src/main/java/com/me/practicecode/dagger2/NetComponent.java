package com.me.practicecode.dagger2;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by user on 2017/7/18.

 */

/**
 * 这里必须要指定作用域
 * 否则会报  @singleton component cannot depend on scoped component 的错误.
 */
@Singleton
@Component(modules = {NetModule.class})
public interface NetComponent {

    void inject(Dagger2DemoActivity activity);
}
