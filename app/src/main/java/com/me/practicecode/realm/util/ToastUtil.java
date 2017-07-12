package com.me.practicecode.realm.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by user on 2017/7/11.
 */

public class ToastUtil {
   static Toast mToast;

    public static void show(Context context,String msg){
        if (mToast == null){
            mToast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();

    }
}
