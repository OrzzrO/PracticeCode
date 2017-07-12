package com.me.practicecode.realm.ui;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.me.practicecode.R;
import com.me.practicecode.realm.RealmBaseActivity;
import com.me.practicecode.realm.bean.User;
import com.me.practicecode.realm.util.RealmHelper;

import butterknife.BindView;

/**
 * Created by user on 2017/7/12.
 */

public class RealmQueryOtherActivity
    extends RealmBaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.tv_average_age)
    TextView mTvAverageAge;
    @BindView(R.id.tv_sum_age)
    TextView mTvSumAge;
    @BindView(R.id.tv_max_id)
    TextView mTvMaxId;
    private RealmHelper mRealmHelper;

    @Override
    public int getLayoutId() {
        return R.layout.activity_realm_query_other;
    }

    @Override
    protected void init() {
        super.init();
        setToolbar(mToolBar,"query other");
        mRealmHelper = new RealmHelper(this);
        getAverage();
        getSumAge();
        getMaxAge();

    }

    private void getMaxAge() {
        Number age = mRealmHelper
            .getRealm()
            .where(User.class)
            .findAll()
            .max("age");
        int age_value = age.intValue();
        mTvMaxId.setText(age_value + "岁");
    }

    /**
     * 获取总年龄
     */
    private void getSumAge() {
        Number age = mRealmHelper
            .getRealm()
            .where(User.class)
            .findAll()
            .sum("age");
        int age_value = age.intValue();
        mTvSumAge.setText( age_value + "岁");
    }

    /**
     * 查询平均年龄
     */
    private void getAverage() {
        double age = mRealmHelper
            .getRealm()
            .where(User.class)
            .findAll()
            .average("age");
        mTvAverageAge.setText( age + "岁");
    }


}
