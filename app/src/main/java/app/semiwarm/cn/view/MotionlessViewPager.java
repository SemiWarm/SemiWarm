package app.semiwarm.cn.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不可滑动的ViewPager
 * 用于实现底部导航
 * Created by alibct on 2017/3/3.
 */

public class MotionlessViewPager extends ViewPager {
    public MotionlessViewPager(Context context) {
        this(context,null);
    }

    public MotionlessViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
