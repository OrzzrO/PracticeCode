package com.me.practicecode.bezierview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.me.practicecode.R;

import static android.R.attr.width;
import static com.me.practicecode.R.attr.height;

/**
 * Created by user on 2017/5/31.
 */

public class BezierViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezierview);

    }
}
