package com.app.smartlibhost.other;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * 使ViewPager不能滑动
 */
public class NoTouchViewPager extends ViewPager {

    public NoTouchViewPager(Context context) {
        super(context);
    }

    public NoTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }
}
