package com.me.practicecode.realm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by user on 2017/7/11.
 */

public abstract class BaseAdapter<T>
    extends RecyclerView.Adapter<BaseViewHolder> {
    Context mContext;
    List<T> mDatas;
    int mLayoutId;

    public BaseAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutId = layoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
            .from(mContext)
            .inflate(mLayoutId, null, false);
        BaseViewHolder holder = new BaseViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        parseData(mContext,holder,mDatas.get(position));
    }

    /**
     *  //让子类自己去set数据
     * @param context
     * @param holder
     * @param t
     */
    protected abstract void parseData(Context context, BaseViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }
}
