package com.me.practicecode.realm.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by user on 2017/7/11.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    SparseArray<View> mItemViewList ;
    View mItemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        mItemViewList = new SparseArray<View>();
    }

    public <T extends View> T getView(int viewId) {
        View view = mItemViewList.get(viewId);
        if (view == null){
            view = mItemView.findViewById(viewId);
            mItemViewList.put(viewId,view);
        }
        return (T) view;
    }

    public BaseViewHolder setText(int viewid,String text){
        TextView textView = getView(viewid);
        textView.setText(text);
        return this;
    }

    public BaseViewHolder setViewVisibility(int viewid, int flag) {
        View view = getView(viewid);
        view.setVisibility(flag);
        return this;
    }
}
