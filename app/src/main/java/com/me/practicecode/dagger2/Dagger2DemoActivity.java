package com.me.practicecode.dagger2;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.me.practicecode.BaseActivity;
import com.me.practicecode.R;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2017/7/17.
 */

public class Dagger2DemoActivity
    extends BaseActivity {


//    @Inject
//    Person mPerson;

    @Inject
    SharedPreferences mSharedPreferences;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_dagger2;
    }

    @Override
    protected void init() {
        super.init();
//                notUseDagger2();
        userDagger2();

    //    DaggerPersonComponent.builder().personModule(new PersonModule()).build().inject(this);
      //  Log.w("hongTest", "init:  person == " + mPerson );


    }

    /**
     * 使用dagger2的初始化
     */
    private void userDagger2() {

        DaggerNetComponent.builder()
            .netModule(new NetModule("http://api.github.com",this))
            .build()
            .inject(this);
        Log.w("hongTest", "userDagger2:  mShare =" + mSharedPreferences );

    }

    /**
     * 未使用dagger2的初始化写法
     */
    private void notUseDagger2() {
        //Enable caching for okhttp
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(getApplication().getCacheDir(), cacheSize);
        OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .cache(cache)
            .build();
        // Used for caching authentication tokens
        SharedPreferences sharedPrefeences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.w("hongTest", "notUseDagger2: sharedP = " + sharedPrefeences );
        //instantiate Gson
        Gson gson = new GsonBuilder().create();
        GsonConverterFactory converterFactory = GsonConverterFactory.create();
        //build retrofit
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.github.com")
            .addConverterFactory(converterFactory)
            .client(client)
            .build();
    }
}
