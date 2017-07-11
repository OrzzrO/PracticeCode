package com.me.practicecode.testview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ShadowOverlayContainer;
import android.support.v17.leanback.widget.ShadowOverlayHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.me.practicecode.R;

/**
 * Created by user on 2017/6/2.
 */

public class TestActivity
    extends AppCompatActivity {


    private ShadowOverlayHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window
            .getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_test);
        init();


    }

    private void init() {
        ImageView img= (ImageView) findViewById(R.id.iv_shadow);
        ViewGroup parentView = (ViewGroup) img.getParent();
        mHelper = new ShadowOverlayHelper.Builder().needsOverlay(true)
                                                   .needsRoundedCorner(true)
                                                   .needsShadow(true)
                                                   .build(this);
        mHelper.prepareParentForShadow(parentView); // apply optical-bounds for 9-patch shadow.
        mHelper.setOverlayColor(img, Color.argb(0x80, 0x80, 0x80, 0x80));
        mHelper.setShadowFocusLevel(img, 1.0f);

//        ShadowOverlayContainer wrapper = mHelper.createShadowOverlayContainer(this);
         initializeView(img);
    }

    View initializeView (View view) {
        if (mHelper.needsWrapper()) {
            ShadowOverlayContainer wrapper = mHelper.createShadowOverlayContainer(this);
            wrapper.wrap(view);
            return wrapper;
        } else {
            mHelper.onViewCreated(view);
            return view;
        }
    }
}
