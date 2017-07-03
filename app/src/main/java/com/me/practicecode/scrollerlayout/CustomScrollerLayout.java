package com.me.practicecode.scrollerlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by user on 2017/6/5.
 */

public class CustomScrollerLayout extends ViewGroup {

	private Scroller scroller;
	private int touchSlop;
    private int leftBorder;
    private int rightBorder;
    private View child1;
    private View child2;
    private float downX;
    private float downY;
    private float moveY;

    public CustomScrollerLayout(Context context) {
		this(context, null);
	}

	public CustomScrollerLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		scroller = new Scroller(context);

        //滑动触发的距离,大于它才可以触发滑动.
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

	}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            measureChildren(widthMeasureSpec,heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

        child1 = getChildAt(0);
        child2 = getChildAt(1);

        child1.layout(0, getMeasuredHeight() - child1.getMeasuredHeight() - 85, getMeasuredWidth(), getMeasuredHeight() - 85);
        child2.layout(0,getMeasuredHeight() - 85,getMeasuredWidth(),getMeasuredHeight() + child2.getMeasuredHeight());


        Log.e("hongTest", "onLayout:  child 1" + child1.getMeasuredHeight() );
        Log.e("hongTest", "onLayout:  child 2" + child2.getMeasuredHeight() );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                if (downY < getMeasuredHeight() - child1.getMeasuredHeight()){

                }else{

                    return true;
                }


                 break;
            case MotionEvent.ACTION_MOVE:
                preformMove(event);
                break;
            case MotionEvent.ACTION_UP:
                proformUp(event);

                moveY = 0;
                downY = 0;
                break;
            default:
                 break;
        }



        return false;
    }

    private void proformUp(MotionEvent event) {

        if (getScrollY() > child2.getMeasuredHeight() / 3){
            scroller.startScroll(0,getScrollY(),0,child2.getMeasuredHeight() - getScrollY(),500);
            invalidate();
        }

    }

    private void preformMove(MotionEvent event) {

        moveY = event.getY();
       int dy = Math.round(downY - moveY);
        float finalY = dy + getScrollY();

        if (finalY < 0){

            scrollTo(0,0);
        }else if (finalY > child2.getMeasuredHeight()){
            scrollTo(0,child2.getMeasuredHeight() );
        }else{

            scrollBy(0,dy);

            downY = moveY;
            invalidate();
        }


    }

    @Override
    public void computeScroll() {

        if (scroller.computeScrollOffset()){

            int currY = scroller.getCurrY();
            scrollTo(0,currY);
            invalidate();
        }


    }
}
