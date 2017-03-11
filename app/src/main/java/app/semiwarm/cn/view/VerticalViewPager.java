package app.semiwarm.cn.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义垂直滑动的ViewPager
 * Created by alibct on 2017/3/3.
 */

public class VerticalViewPager extends ViewPager {
    public VerticalViewPager(Context context) {
        this(context, null);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(swapTouchEvent(MotionEvent.obtain(ev)));
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapTouchEvent(MotionEvent.obtain(ev)));
    }

    /**
     * 用于反转滑动操作，使水平的操作变为竖直的操作
     */
    private MotionEvent swapTouchEvent(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();
        event.setLocation((event.getY() / height) * width, (event.getX() / width) * height);
        return event;
    }

    // 滑动模式
    public static class VerticalTransformer implements PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            float alpha = 0;
            if (0 <= position && 1 >= position) {
                alpha = 1 - position;
            } else if (-1 < position && 0 > position) {
                alpha = 1 + position;
            }
            page.setAlpha(alpha);
            page.setTranslationX(page.getWidth() * -position);
            page.setTranslationY(page.getHeight() * position);
        }
    }
}
