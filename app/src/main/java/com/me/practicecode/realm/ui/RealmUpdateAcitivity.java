package com.me.practicecode.realm.ui;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.me.practicecode.R;
import com.me.practicecode.realm.RealmBaseActivity;
import com.me.practicecode.realm.util.RealmHelper;
import com.me.practicecode.realm.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2017/7/12.
 */

public class RealmUpdateAcitivity
    extends RealmBaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.btn_query_update)
    Button mBtnQueryUpdate;
    private RealmHelper mRealmHelper;
    private int mId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_realm_update;
    }

    @Override
    protected void init() {
        super.init();

        setToolbar(mToolBar,"update data");

        mId = getIntent().getIntExtra("id", 0);
        mRealmHelper = new RealmHelper(this);

    }



    @OnClick(R.id.btn_query_update)
    public void onViewClicked() {
        String trim = mEtName
            .getText()
            .toString()
            .trim();
        if (TextUtils.isEmpty(trim)){
            ToastUtil.show(this,"姓名不能为空");
            return;
        }
//        Log.w("hongTest", "onViewClicked:  update界面,更新数据~~~" );
        mRealmHelper.updateUser(mId,trim);
        setResult(RESULT_OK);
        finish();
    }
}
