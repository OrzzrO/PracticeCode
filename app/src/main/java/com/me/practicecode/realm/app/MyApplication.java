package com.me.practicecode.realm.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by user on 2017/7/11.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initRealm();

    }

    /**
     * 初始化realm数据库.
     */
    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
            //指定数据库的名字,默认为default.realm
            .name("myrealm.realm")
            //当版本冲突的时候删除数据库
            .deleteRealmIfMigrationNeeded()
            .build();
        Realm.setDefaultConfiguration(config);
    }
}
