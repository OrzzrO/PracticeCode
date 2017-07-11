package com.me.practicecode.realm.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.me.practicecode.R;
import com.me.practicecode.realm.RealmBaseActivity;
import com.me.practicecode.realm.adapter.UserAdapter;
import com.me.practicecode.realm.bean.User;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by user on 2017/7/11.
 */

public class RealmAddActivity
    extends RealmBaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private ArrayList<User> mDatas;

    @Override
    public int getLayoutId() {
        return R.layout.activity_realm_add;
    }

    @Override
    protected void init() {
        super.init();
        setToolbar(mToolBar, "add data to Realm");
        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        UserAdapter userAdapter = new UserAdapter(this, mDatas, R.layout.item_user);
        mRecyclerview.setAdapter(userAdapter);

    }

    private void initData() {
        mDatas = new ArrayList<>();
        User user1 = new User();
        user1.setAge(1);
        user1.setName("tony");
        user1.setId(001);
        mDatas.add(user1);
        User user2 = new User();
        user2.setAge(2);
        user2.setName("lina");
        user2.setId(002);
        mDatas.add(user2);
        User user3 = new User();
        user3.setAge(3);
        user3.setName("zeus");
        user3.setId(003);
        mDatas.add(user3);
        User user4 = new User();
        user4.setAge(4);
        user4.setName("doom");
        user4.setId(004);
        mDatas.add(user4);
        User user5 = new User();
        user5.setAge(5);
        user5.setName("viper");
        user5.setId(005);
        mDatas.add(user5);
        User user6 = new User();
        user6.setAge(6);
        user6.setName("qop");
        user6.setId(006);
        mDatas.add(user6);
        User user7 = new User();
        user7.setAge(7);
        user7.setName("invoker");
        user7.setId(007);
        mDatas.add(user7);
        User user8 = new User();
        user8.setAge(8);
        user8.setName("miracle");
        user8.setId(010);
        mDatas.add(user8);


    }


}
