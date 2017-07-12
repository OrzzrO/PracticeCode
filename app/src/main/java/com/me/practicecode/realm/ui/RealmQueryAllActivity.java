package com.me.practicecode.realm.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.me.practicecode.R;
import com.me.practicecode.realm.RealmBaseActivity;
import com.me.practicecode.realm.adapter.BaseAdapter;
import com.me.practicecode.realm.adapter.QueryAllAdapter;
import com.me.practicecode.realm.bean.User;
import com.me.practicecode.realm.util.DefaultItemTouchHelpCallback;
import com.me.practicecode.realm.util.RealmHelper;

import java.util.List;

import butterknife.BindView;

/**
 * Created by user on 2017/7/12.
 */

public class RealmQueryAllActivity extends RealmBaseActivity {
    private static final int REQUEST_UPDATE = 0;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private RealmHelper mRealmHelper;
    private List<User> mUsers;
    private QueryAllAdapter mAllAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_realm_queryall;
    }

    @Override
    protected void init() {
        super.init();
        setToolbar(mToolBar, "All Data");
        initData();
        initListener();

    }

    private void initListener() {
        mAllAdapter.setonItemClickListener(new BaseAdapter.onItemClickListener() {
            @Override
            public void itemClick(View v, int position) {
                if (mUsers != null) {
                    User user = mUsers.get(position);
                    Intent intent = new Intent(RealmQueryAllActivity.this, RealmUpdateAcitivity.class);
                    intent.putExtra("id", user.getId());
                    startActivityForResult(intent, REQUEST_UPDATE);
                }
            }
        });
    }

    private void initData() {
        mRealmHelper = new RealmHelper(this);
        //查询所有的数据
        mUsers = mRealmHelper.queryAll();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAllAdapter = new QueryAllAdapter(this, mUsers, R.layout.item_user);
        mRecyclerview.setAdapter(mAllAdapter);
        setSwipeDelete();
    }

    private void setSwipeDelete() {
        DefaultItemTouchHelpCallback callback = new DefaultItemTouchHelpCallback(new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
            /**
             * 滑动的回调
             * @param adapterPosition item的position
             */
            @Override
            public void onSwiped(int adapterPosition) {
                //删除数据库的数据
                mRealmHelper.deleteUser(mUsers.get(adapterPosition).getId());
                //删除显示的列表中的数据
                mUsers.remove(adapterPosition);
                //数据变动,通知adapter更新数据
                mAllAdapter.notifyItemRemoved(adapterPosition);
            }

            @Override
            public boolean onMove(int srcPosition, int targetPosition) {
                return false;
            }
        });
        callback.setDragEnable(false);
        callback.setSwipeEnable(true);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //将recyclerView与callback关联起来
        touchHelper.attachToRecyclerView(mRecyclerview);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            //            Log.w("hongTest", "onActivityResult:  数据库更新了,删除原数据" );
            mUsers.clear();
            //            Log.w("hongTest", "onActivityResult:  数据库更新了,重新查询数据" );
            List<User> users = mRealmHelper.queryAll();
            mUsers.addAll(users);
            //            Log.w("hongTest", "onActivityResult:  数据库更新了,通知adapter更新recyclerview" );
            mAllAdapter.notifyDataSetChanged();
        }

    }
}
