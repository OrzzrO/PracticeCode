package com.me.practicecode.realm.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.me.practicecode.R;
import com.me.practicecode.realm.RealmBaseActivity;
import com.me.practicecode.realm.adapter.QueryAllAdapter;
import com.me.practicecode.realm.bean.User;
import com.me.practicecode.realm.util.RealmHelper;
import com.me.practicecode.realm.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2017/7/12.
 */

public class RealmQueryConditionActivity
    extends RealmBaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.et_id)
    EditText mEtId;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.et_age)
    EditText mEtAge;
    @BindView(R.id.btn_query_id)
    Button mBtnQueryId;
    @BindView(R.id.btn_query_age)
    Button mBtnQueryAge;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private RealmHelper mRealmHelper;
    private List<User> mUsers =new ArrayList<>();
    private QueryAllAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_realm_query_condition;
    }

    @Override
    protected void init() {
        super.init();

        setToolbar(mToolBar,"condition query");
        mRealmHelper = new RealmHelper(this);
        mAdapter = new QueryAllAdapter(this, mUsers, R.layout.item_user);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

    }


    @OnClick({R.id.btn_query_id,
              R.id.btn_query_age})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_query_id:
                String id = mEtId
                    .getText()
                    .toString()
                    .trim();

                if (TextUtils.isEmpty(id)){
                    ToastUtil.show(this,"请输入id id不能为空");
                    return;
                }
                User user = mRealmHelper.queryUserById(Integer.parseInt(id));

                if (user != null){
                    mUsers.clear();
                    mUsers.add(user);
                    mAdapter.notifyDataSetChanged();
                }else{
                    ToastUtil.show(this,"查询结果为空");
                }




                break;
            case R.id.btn_query_age:
                String age = mEtId
                    .getText()
                    .toString()
                    .trim();

                if (TextUtils.isEmpty(age)){
                    ToastUtil.show(this,"请输入age age不能为空");
                    return;
                }
                List<User> users = mRealmHelper.queryUserByAge(Integer.parseInt(age));
                if (users != null){
                    mUsers.clear();
                    mUsers.addAll(users);
                    mAdapter.notifyDataSetChanged();
                }else{
                    ToastUtil.show(this,"查询结果为空");
                }

                break;
        }
    }
}
