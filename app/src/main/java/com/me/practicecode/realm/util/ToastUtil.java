package com.me.practicecode.realm.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by user on 2017/7/11.
 */

public class ToastUtil {
   static Toast mToast;

    public static void show(Context context,String text){
        if (mToast == null){
            mToast = new Toast(context);
        }
        mToast.setText(text);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();

    }
}
