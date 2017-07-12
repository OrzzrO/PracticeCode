package com.me.practicecode.realm.async;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.me.practicecode.R;
import com.me.practicecode.realm.RealmBaseActivity;
import com.me.practicecode.realm.adapter.AsyncQueryAllAdapter;
import com.me.practicecode.realm.adapter.BaseAdapter;
import com.me.practicecode.realm.bean.User;
import com.me.practicecode.realm.util.DefaultItemTouchHelpCallback;
import com.me.practicecode.realm.util.RealmHelper;
import com.me.practicecode.realm.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by user on 2017/7/12.
 */

public class AsyncQueryActivity
    extends RealmBaseActivity {
    private static final int REQUEST_UPDATE = 0;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private RealmHelper mRealmHelper;

    private List<User> mUsers = new ArrayList<>();
    private AsyncQueryAllAdapter mAllAdapter;
    private RealmAsyncTask mRealmDeleteAsyncTask;
    private RealmResults<User> users;

    @Override
    public int getLayoutId() {
        return R.layout.activity_realm_async_query;
    }

    @Override
    protected void init() {
        super.init();

        setToolbar(mToolBar,"async query");
        mRealmHelper = new RealmHelper(this);

        initData();
        initRecyclerView();
        initListener();


    }

    private void initListener() {
        mAllAdapter.setonItemClickListener(new BaseAdapter.onItemClickListener() {
            @Override
            public void itemClick(View v, int position) {
                User user = mUsers.get(position);
                Intent intent = new Intent(AsyncQueryActivity.this,AysncUpdateActivity.class);
                intent.putExtra("id", user.getId());
                startActivityForResult(intent, REQUEST_UPDATE);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAllAdapter = new AsyncQueryAllAdapter(this, mUsers, R.layout.item_user);
        mRecyclerview.setAdapter(mAllAdapter);
        setSwipeDelete();

    }

    private void initData() {
        users = mRealmHelper
            .getRealm()
            .where(User.class)
            .findAllAsync();
        users.addChangeListener(new RealmChangeListener<RealmResults<User>>() {
            @Override
            public void onChange(RealmResults<User> element) {
                //通过id排序
                element.sort("id");
                List<User> users1 = mRealmHelper
                    .getRealm()
                    .copyFromRealm(element);
                mUsers.clear();
                mUsers.addAll(users1);
                mAllAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setSwipeDelete() {
        DefaultItemTouchHelpCallback callback = new DefaultItemTouchHelpCallback(new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
            /**
             * 滑动的回调
             * @param adapterPosition item的position
             */
            @Override
            public void onSwiped(int adapterPosition) {
                //异步删除数据库的数据
                asyncDeleteData(mUsers.get(adapterPosition).getId());

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

    /**
     * 异步删除
     * @param id
     */
    private void asyncDeleteData(final int id) {
            mRealmDeleteAsyncTask = mRealmHelper
                .getRealm()
                .executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        User user = realm
                            .where(User.class)
                            .equalTo("id", id)
                            .findFirst();
                        if (user != null){
                            user.deleteFromRealm();
                        }
                    }

                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        ToastUtil.show(AsyncQueryActivity.this, "删除数据成功");
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        ToastUtil.show(AsyncQueryActivity.this, "删除数据失败");
                    }
                });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            initData();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        users.removeChangeListeners();
        mRealmHelper.cancelAsyncRealTask(mRealmDeleteAsyncTask);

    }
}
