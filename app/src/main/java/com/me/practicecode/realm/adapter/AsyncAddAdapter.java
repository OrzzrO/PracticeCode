package com.me.practicecode.realm.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.me.practicecode.R;
import com.me.practicecode.realm.bean.User;
import com.me.practicecode.realm.util.RealmHelper;
import com.me.practicecode.realm.util.ToastUtil;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

/**
 * Created by user on 2017/7/12.
 */

public class AsyncAddAdapter
    extends BaseAdapter<User> {

    private Context mContext;
    private final RealmHelper mRealmHelper;
    private RealmAsyncTask mRealmAddAsyncTask;
    private RealmAsyncTask mRealmDeleteAsyncTask;

    public AsyncAddAdapter(Context context, List<User> datas, int layoutId) {
        super(context, datas, layoutId);
        this.mContext = context;
        mRealmHelper = new RealmHelper(context);
    }

    @Override
    protected void parseData(Context context, BaseViewHolder holder, final User user) {
        holder
            .setText(R.id.tv_id, user.getId() + "")
            .setText(R.id.tv_name, user.getName());
        final ImageView img = holder.getView(R.id.iv_like);
        if (mRealmHelper.isUserExist(user.getId())) {
            img.setSelected(true);
        } else {
            img.setSelected(false);
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img.isSelected()) {
                    img.setSelected(false);
                    asyncDeleteData(img,user.getId());
                }else{
                    img.setSelected(true);
                    asyncAddData(img,user);
                }
            }
        });
    }

    /**
     * 异步添加数据
     * @param img
     * @param user
     */
    private void asyncAddData(final ImageView img, final User user) {
        mRealmAddAsyncTask = mRealmHelper
            .getRealm()
            .executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(user);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    img.setSelected(true);
                    ToastUtil.show(mContext, "添加数据成功");
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    ToastUtil.show(mContext, "添加数据失败");

                }
            });
    }

    /**
     * 异步删除数据
     * @param img
     * @param id
     */
    private void asyncDeleteData(final ImageView img, final int id) {
        mRealmDeleteAsyncTask = mRealmHelper
            .getRealm()
            .executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    User user = realm
                        .where(User.class)
                        .equalTo("id", id)
                        .findFirst();
                    user.deleteFromRealm();
                }

            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    img.setSelected(false);
                    ToastUtil.show(mContext, "删除数据成功");
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    ToastUtil.show(mContext, "删除数据失败");
                }
            });
    }

    /**
     * 取消异步任务
     */
    public void cancelAsync(){

        mRealmHelper.cancelAsyncRealTask(mRealmAddAsyncTask);
        mRealmHelper.cancelAsyncRealTask(mRealmDeleteAsyncTask);
    }
}
