package app.semiwarm.cn.view;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义商品详情页
 * Created by alibct on 2017/5/17.
 */

public class GoodsView extends ViewGroup {

    private static final int SPEED_THRESHOLD = 6000;  // 上下滑动速度阈值
    private int DISTANCE_THRESHOLD = 60;            // 单位是dp，当上下滑动速度不够时，通过这个阈值来判定是应该粘到顶部还是底部


    private ViewDragHelper mDragHelper;
    private GestureDetectorCompat mGestureDetector;
    private View goodsInfoView;
    private View goodsDetailView;
    private int viewHeight;
    private int currentPage;
    private boolean onTop = false;


    public interface OnNextPageListener {
        void onNextPage();
    }

    public void setOnNextPageListener(OnNextPageListener onNextPageListener) {
        mOnNextPageListener = onNextPageListener;
    }

    public OnNextPageListener mOnNextPageListener; // 手指松开是否加载下一页的监听器

    public interface OnTopListener {
        void onTop();
    }

    public void setOnTopListener(OnTopListener onTopListener) {
        mOnTopListener = onTopListener;
    }

    public OnTopListener mOnTopListener;          // 快速返回顶部的监听

    public GoodsView(Context context) {
        this(context, null);
    }

    public GoodsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DISTANCE_THRESHOLD = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DISTANCE_THRESHOLD, getResources().getDisplayMetrics());
        mDragHelper = ViewDragHelper.create(this, 10f, new DragCallBack());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM);
        mGestureDetector = new GestureDetectorCompat(getContext(), new YScrollDetector());
        currentPage = 1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 初始化商品信息和商品详情界面
        if (null == goodsInfoView) {
            goodsInfoView = getChildAt(0);
        }
        if (null == goodsDetailView) {
            goodsDetailView = getChildAt(1);
        }
        // 当滑动到商品详情页时，商品详情页的top值为0，此时商品信息页的top值为负数
        if (goodsInfoView.getTop() == 0) {
            goodsInfoView.layout(0, 0, r, b);
            goodsDetailView.layout(0, 0, r, b);
            viewHeight = goodsInfoView.getMeasuredHeight();
            goodsDetailView.offsetTopAndBottom(viewHeight);
        } else {
            goodsInfoView.layout(goodsInfoView.getLeft(), goodsInfoView.getTop(), goodsInfoView.getRight(), goodsInfoView.getBottom());
            goodsDetailView.layout(goodsDetailView.getLeft(), goodsDetailView.getTop(), goodsDetailView.getRight(), goodsDetailView.getBottom());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean shouldIntercept = false;
        boolean yScroll = mGestureDetector.onTouchEvent(ev);
        try {
            shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shouldIntercept && yScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private class DragCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            // 两个View都需要跟踪
            return true;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == goodsInfoView) {
                goodsDetailView.offsetTopAndBottom(dy);
            }

            if (changedView == goodsDetailView) {
                goodsInfoView.offsetTopAndBottom(dy);
            }
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            // 这个用来控制拖拽过程中松手后，自动滑行的速度
            return child.getHeight();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            // 滑动松开后，需要向上或者向下粘到特定的位置, 默认是粘到最顶端
            int finalTop = 0;
            if (releasedChild == goodsInfoView) {
                // 拖动商品信息页面松手
                if (yvel < -SPEED_THRESHOLD || releasedChild.getTop() < -DISTANCE_THRESHOLD) {
                    // 向上的速度足够大或者向上滑动的距离超过某个阈值，就滑动到顶端
                    finalTop = -viewHeight;
                    // 下一页可以初始化了
                    if (null != mOnNextPageListener) {
                        mOnNextPageListener.onNextPage();
                    }
                }
            } else {
                // 拖动商品详情页面松手
                if (yvel > SPEED_THRESHOLD || releasedChild.getTop() > DISTANCE_THRESHOLD) {
                    // 向下滚动到初始状态
                    finalTop = viewHeight;
                }
            }
            //触发缓慢滚动
            if (mDragHelper.smoothSlideViewTo(releasedChild, 0, finalTop)) {
                ViewCompat.postInvalidateOnAnimation(GoodsView.this);
                onTop = false;
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //如果不想要滑动顶端还能拉出空白，打开下面两行注释即可
            //if (child == view1 && top >= 0 && dy > 0) return 0;
            //if (child == view2 && top <= 0 && dy < 0) return 0;
            // 阻尼滑动，让滑动位移将为1/2
            return child.getTop() + dy / 2;
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
            if (goodsDetailView.getTop() == 0) {
                currentPage = 2;
            } else if (goodsInfoView.getTop() == 0) {
                currentPage = 1;
            }
        }
    }

    /**
     * 滚动到顶部
     */
    public void goTop(OnTopListener onTopListener) {
        mOnTopListener = onTopListener;
        if (mOnTopListener != null) {
            mOnTopListener.onTop();
        }
        if (currentPage == 2) {
            //触发缓慢滚动
            if (mDragHelper.smoothSlideViewTo(goodsDetailView, 0, viewHeight)) {
                ViewCompat.postInvalidateOnAnimation(this);
                onTop = true;
            }
        }
    }

    private class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // 垂直滑动时dy>dx，才被认定是上下拖动
            return Math.abs(distanceY) > Math.abs(distanceX);
        }
    }
}
