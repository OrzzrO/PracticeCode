package com.me.practicecode.realm.adapter;

import android.content.Context;
import android.view.View;

import com.me.practicecode.R;
import com.me.practicecode.realm.bean.User;

import java.util.List;

/**
 * Created by user on 2017/7/12.
 */

public class QueryAllAdapter extends BaseAdapter<User> {


    public QueryAllAdapter(Context context, List<User> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected void parseData(Context context, BaseViewHolder holder, User user) {
        holder.setText(R.id.tv_name,user.getName())
            .setText(R.id.tv_id,user.getId()+"")
            .setViewVisibility(R.id.iv_like, View.INVISIBLE);
    }
}
