package com.me.practicecode.realm.adapter;

import android.content.Context;
import android.view.View;

import com.me.practicecode.R;
import com.me.practicecode.realm.bean.User;
import com.me.practicecode.realm.util.RealmHelper;

import java.util.List;

import io.realm.RealmAsyncTask;

/**
 * Created by user on 2017/7/12.
 */

public class AsyncQueryAllAdapter extends BaseAdapter<User> {
    Context mContext;
    private final RealmHelper mRealmHelper;
    private RealmAsyncTask mRealmDeleteAsyncTask;

    public AsyncQueryAllAdapter(Context context, List<User> datas, int layoutId) {
        super(context, datas, layoutId);
        this.mContext = context;
        mRealmHelper = new RealmHelper(context);
    }

    @Override
    protected void parseData(Context context, BaseViewHolder holder, User user) {
        holder.setText(R.id.tv_name, user.getName())
              .setText(R.id.tv_id,user.getId()+"")
              .setViewVisibility(R.id.iv_like, View.INVISIBLE);

    }



}
