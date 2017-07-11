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

public class RealmTestActivity
    extends RealmBaseActivity {


    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.btn_add)
    Button mBtnAdd;
    @BindView(R.id.btn_query_and_modify)
    Button mBtnQueryAndModify;
    @BindView(R.id.btn_async)
    Button mBtnAsync;


    @Override
    protected void init() {
        super.init();

        setToolbar(mToolBar,"realm-list");
//        Log.w("hongTest", "init:  toolbar = " + mToolBar);
//        Log.w("hongTest", "init:  button = " + mBtnAdd);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_realm;
    }


    @OnClick({R.id.btn_add,
              R.id.btn_query_and_modify,
              R.id.btn_async})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                startActivity(RealmAddActivity.class);
                break;
            case R.id.btn_query_and_modify:
                break;
            case R.id.btn_async:
                break;
        }
    }

}
