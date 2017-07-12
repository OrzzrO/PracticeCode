package com.me.practicecode.realm.async;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.me.practicecode.R;
import com.me.practicecode.realm.RealmBaseActivity;
import com.me.practicecode.realm.adapter.AsyncAddAdapter;
import com.me.practicecode.realm.bean.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by user on 2017/7/12.
 */

public class AsyncAddActivity
    extends RealmBaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private String[] letters=new String[]{"A","B","C","D","E","F","G","H","I","J","K","M","N","U","X","Y","Z"};
    private String[] letters1=new String[]{"a","c","u","p","q","y"};
    private List<User> mUsers=new ArrayList<>();
    private AsyncAddAdapter mAddAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_realm_async_add;
    }

    @Override
    protected void init() {
        super.init();
        setToolbar(mToolBar,"async realm add or delete");

        initData();

        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAddAdapter = new AsyncAddAdapter(this, mUsers, R.layout.item_user);
        mRecyclerview.setAdapter(mAddAdapter);

    }

    private void initData() {
        for (int i=0;i<15;i++){
            User user=new User();
            String name=letters[i]+letters1[i%5]+letters1[i%3];
            user.setName(name);
            user.setAge(i%4);
            user.setId(Integer.parseInt("2017"+i));
            mUsers.add(user);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mAddAdapter != null){
            mAddAdapter.cancelAsync();
        }
    }
}
