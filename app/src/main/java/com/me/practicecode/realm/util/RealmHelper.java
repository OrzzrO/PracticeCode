package com.me.practicecode.realm.util;

import android.content.Context;

import com.me.practicecode.realm.bean.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

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

    /**
     *  改
     * @param id
     * @param str
     */
    public void updateUser(int id,String str){
        User user = mRealm
            .where(User.class)
            .equalTo("id", id)
            .findFirst();
        mRealm.beginTransaction();
        user.setName(str);
        mRealm.commitTransaction();

    }

    /**
     * 查询所有
     * @return 返回数据集合.
     */
    public List<User> queryAll(){
        RealmResults<User> users = mRealm
            .where(User.class)
            .findAll();

        //对查询结果进行排序.
        //通过id进行排序,默认是增序,
        //可以降序
        users = users.sort("id");
        return mRealm.copyFromRealm(users);
    }

    //通过id查询
    public User queryUserById(int id){
        User user = mRealm
            .where(User.class)
            .equalTo("id", id)
            .findFirst();
        return user;
    }

    //通过年龄查询
    public List<User> queryUserByAge(int age){
        RealmResults<User> users = mRealm
            .where(User.class)
            .equalTo("age", age)
            .findAll();
        return mRealm.copyFromRealm(users);
    }



    public boolean isUserExist(int id){
        User user = mRealm
            .where(User.class)
            .equalTo("id", id)
            .findFirst();
        return user == null ? false : true;
    }

    public Realm getRealm(){
        return mRealm;
    }

    /**
     * 取消任务
     * @param task
     */
    public void cancelAsyncRealTask(RealmAsyncTask task) {
        if (task != null && !task.isCancelled()){
            task.cancel();
        }
    }
}
