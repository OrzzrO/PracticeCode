package com.me.practicecode.realm.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.me.practicecode.R;
import com.me.practicecode.realm.RealmBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2017/7/11.
 */

public class RealmQueryActivity
    extends RealmBaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.btn_query_all)
    Button mBtnQueryAll;
    @BindView(R.id.btn_query_condition)
    Button mBtnQueryCondition;
    @BindView(R.id.btn_query_other)
    Button mBtnQueryOther;

    @Override
    public int getLayoutId() {
        return R.layout.activity_realm_query;
    }

    @Override
    protected void init() {
        super.init();
        setToolbar(mToolBar,"query");
    }

    @OnClick({R.id.btn_query_all,
              R.id.btn_query_condition,
              R.id.btn_query_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_query_all:
                startActivity(RealmQueryAllActivity.class);
                break;
            case R.id.btn_query_condition:
                startActivity(RealmQueryConditionActivity.class);
                break;
            case R.id.btn_query_other:
                startActivity(RealmQueryOtherActivity.class);
                break;
        }
    }
}
