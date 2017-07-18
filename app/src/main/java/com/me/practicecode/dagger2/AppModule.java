package com.me.practicecode.dagger2;

import android.content.Context;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 2017/7/18.
 */


@Module
public class AppModule {

    Context mContext;

    public AppModule(Context context){
        this.mContext = context;
    }


    /**
     * provides告诉dagger2他可以提供Context的实例
     * singleton告诉dagger2他在全局唯一,只被初始化一次.
     * @return
     */
    @Provides
    @Singleton
    Context getContext(){
        Log.w("hongTest", "providesApplication:  invoke~" );
        return mContext;
    }


}
