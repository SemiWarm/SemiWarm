package app.semiwarm.cn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class VerticalListView extends ListView implements ObservableView {

    private float downX;
    private float downY;

    public VerticalListView(Context context) {
        this(context, null);
    }

    public VerticalListView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.listViewStyle);
    }

    public VerticalListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                //如果滑动到了最底部，就允许继续向上滑动加载下一页，否者不允许
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = ev.getX() - downX;
                float dy = ev.getY() - downY;
                boolean allowParentTouchEvent;
                if (Math.abs(dy) > Math.abs(dx)) {
                    //垂直方向滑动
                    if (dy > 0) {
                        //位于顶部时下拉，让父View消费事件
                        allowParentTouchEvent = isTop();
                    } else {
                        //位于底部时上拉，让父View消费事件
                        allowParentTouchEvent = isBottom();
                    }
                } else {
                    //水平方向滑动
                    allowParentTouchEvent = true;
                }
                getParent().requestDisallowInterceptTouchEvent(!allowParentTouchEvent);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean isTop() {
        if (getChildCount() <= 0) return false;
        final int firstTop = getChildAt(0).getTop();
        return getFirstVisiblePosition() == 0 && firstTop >= getListPaddingTop();
    }

    @Override
    public boolean isBottom() {
        final int childCount = getChildCount();
        if (childCount <= 0) return false;
        final int itemsCount = getCount();
        final int firstPosition = getFirstVisiblePosition();
        final int lastPosition = firstPosition + childCount;
        final int lastBottom = getChildAt(childCount - 1).getBottom();
        return lastPosition >= itemsCount && lastBottom <= getHeight() - getListPaddingBottom();
    }

    @Override
    public void goTop() {
        setSelection(0);
    }
}
