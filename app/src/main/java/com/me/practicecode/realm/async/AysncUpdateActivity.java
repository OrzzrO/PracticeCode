package com.me.practicecode.realm.async;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.me.practicecode.R;
import com.me.practicecode.realm.RealmBaseActivity;
import com.me.practicecode.realm.bean.User;
import com.me.practicecode.realm.util.RealmHelper;
import com.me.practicecode.realm.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmAsyncTask;

/**
 * Created by user on 2017/7/12.
 */

public class AysncUpdateActivity
    extends RealmBaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.btn_update)
    Button mBtnUpdate;
    private RealmHelper mRealmHelper;
    private int mId;
    private RealmAsyncTask mRealmUpdateAsyncTask;

    @Override
    public int getLayoutId() {
        return R.layout.activity_realm_async_update;
    }

    @Override
    protected void init() {
        super.init();

        setToolbar(mToolBar,"async update");

        mRealmHelper = new RealmHelper(this);
        mId = getIntent().getIntExtra("id", 0);
    }


    @OnClick(R.id.btn_update)
    public void onViewClicked() {
        String name = mEtName
            .getText()
            .toString()
            .trim();

        if (TextUtils.isEmpty(name)){
            ToastUtil.show(this,"姓名不能为空");
            return;
        }
        asyncUpdateData(name);
    }

    private void asyncUpdateData(final String name) {
        mRealmUpdateAsyncTask = mRealmHelper
            .getRealm()
            .executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    User user = realm
                        .where(User.class)
                        .equalTo("id", mId)
                        .findFirst();
                    user.setName(name);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    ToastUtil.show(AysncUpdateActivity.this, "更新成功");
                    setResult(RESULT_OK);
                    finish();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    ToastUtil.show(AysncUpdateActivity.this, "更新失败");
                }
            });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmHelper.cancelAsyncRealTask(mRealmUpdateAsyncTask);
    }
}
