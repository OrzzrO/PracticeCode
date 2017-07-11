package com.me.practicecode.realm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.me.practicecode.R;
import com.me.practicecode.realm.bean.User;
import com.me.practicecode.realm.util.RealmHelper;

import java.util.List;

/**
 * Created by user on 2017/7/11.
 */

public class UserAdapter extends BaseAdapter<User> {

    private final RealmHelper mRealmHelper;

    public UserAdapter(Context context, List<User> datas, int layoutId) {
        super(context, datas, layoutId);
        mRealmHelper = new RealmHelper();
    }


    @Override
    protected void parseData(Context context, BaseViewHolder holder, final User user) {
            holder.setText(R.id.tv_id,user.getId()+"")
                .setText(R.id.tv_name,user.getName());

        final ImageView iv_like = holder.getView(R.id.iv_like);
        if (mRealmHelper.isUserExist(user.getId())){
            iv_like.setSelected(true);
            Log.w("hongTest", "添加界面 user数据已经存在" );
        }else{
            iv_like.setSelected(false);
        }

        iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (iv_like.isSelected()){
                    iv_like.setSelected(false);
                    mRealmHelper.deleteUser(user.getId());
                    Log.w("hongTest", "onClick: 将已存在的数据从realm数据库,执行删除~" );
                }else{
                    iv_like.setSelected(true);
                    mRealmHelper.addUser(user);
                    Log.w("hongTest", "onClick: 数据库中不存在该数据,执行添加~" );
                }
            }
        });

    }
}
