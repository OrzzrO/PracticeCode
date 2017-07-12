package com.me.practicecode.realm.async;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.me.practicecode.R;
import com.me.practicecode.realm.RealmBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2017/7/12.
 */

public class RealmAsyncActivity
    extends RealmBaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.btn_query_add_delete)
    Button mBtnQueryAddDelete;
    @BindView(R.id.btn_query_modify_query)
    Button mBtnQueryModifyQuery;

    @Override
    public int getLayoutId() {
        return R.layout.activity_realm_async;
    }

    @Override
    protected void init() {
        super.init();
        setToolbar(mToolBar,"async realm");
    }


    @OnClick({R.id.btn_query_add_delete,
              R.id.btn_query_modify_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_query_add_delete:
                startActivity(AsyncAddActivity.class);
                break;
            case R.id.btn_query_modify_query:
                startActivity(AsyncQueryActivity.class);
                break;
        }
    }
}
