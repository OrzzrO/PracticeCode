package com.me.practicecode.dagger2;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 2017/7/18.
 */

@Module
public class NetModule {

    String mBaseUrl;
    Context mContext;


    public NetModule(String baseUrl,Context con) {
        mBaseUrl = baseUrl;
        this.mContext = con;
    }

    @Provides
    public Application providesApplication(){
        return new Application();
    }


    @Provides
    @Singleton
    SharedPreferences providesSharedPreference() {
        Log.w("hongTest","providesSharedPreference:  app = " + mContext);

        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Provides
    @Singleton
    Gson providesGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }





}
