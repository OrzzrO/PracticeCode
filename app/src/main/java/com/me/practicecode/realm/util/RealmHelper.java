package com.me.practicecode.realm.util;

import android.content.Context;

import com.me.practicecode.realm.bean.User;

import io.realm.Realm;

/**
 * Created by user on 2017/7/11.
 */

public class RealmHelper {

    Realm mRealm;
    Context mContext;

    public RealmHelper(Context context) {
        mRealm = Realm.getDefaultInstance();
        this.mContext = context;
    }

    /**
     *      增
     * @param user
     */
    public void addUser(final User user){
//
//        //添加对象的两种方式
//        // 1.使用commit提交
//        mRealm.beginTransaction();
//        mRealm.copyToRealm(user);
//        mRealm.commitTransaction();


        //2.使用事务代码块
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(user);
            }
        });
    }

    /**
     *    删
     */
    public void deleteUser(int id){
        User user = mRealm
            .where(User.class)
            .equalTo("id", id)
            .findFirst();

        if (user == null){
            ToastUtil.show(mContext,"数据库没数据,删毛啊~");
            return;
        }

        mRealm.beginTransaction();
        user.deleteFromRealm();
        mRealm.commitTransaction();
    }



    public boolean isUserExist(int id){
        User user = mRealm
            .where(User.class)
            .equalTo("id", id)
            .findFirst();
        return user == null ? false : true;
    }

}
