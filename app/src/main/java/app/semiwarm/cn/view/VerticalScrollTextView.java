package app.semiwarm.cn.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.semiwarm.cn.R;
import app.semiwarm.cn.entity.RotationNotice;
import app.semiwarm.cn.utils.SizeConvertUtils;

/**
 * 自定义垂直滚动的TextView
 * Created by alibct on 2017/3/29.
 */

public class VerticalScrollTextView extends AppCompatTextView {

    private Integer mSpeed; // 文字轮播的速度
    private Integer mInterval; // 文字停留的时间
    private Integer mTitleColor; // 轮播标题颜色
    private Integer mContentColor; // 轮播内容颜色
    private Integer mTitleSize; // 轮播标题字体大小
    private Integer mContentSize; // 轮播内容字体大小
    private List<RotationNotice> mRotationNoticeList; // 轮播通知源

    private Integer mY = 0; // 文字的Y坐标
    private Integer mIndex = 0; // 当前轮播数据的下标
    private Paint mTitlePaint; // 绘制标题的画笔
    private Paint mContentPaint; // 绘制内容的画笔

    private boolean isMove = true; // 文字是否移动

    private boolean isInit = false; // 是否已经初始化文字的纵坐标
    private boolean isPaused = false; // 是否处于停顿状态

    private Rect mTitleBound;
    private Rect mContentBound;

    public interface OnItemClickListener {
        void onClick(Long noticeId);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public VerticalScrollTextView(Context context) {
        this(context, null);
    }

    public VerticalScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainStyledAttributes(attrs);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureTextWidth(widthMeasureSpec);
        int height = measureTextHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null != mRotationNoticeList) {
            RotationNotice notice = mRotationNoticeList.get(mIndex);
            String noticeTitle = notice.getNoticeTitle();
            String noticeDesc = notice.getNoticeDesc();
            // 绘制标题
            mTitlePaint.getTextBounds(noticeTitle, 0, noticeTitle.length(), mTitleBound);
            // 绘制内容
            mContentPaint.getTextBounds(noticeDesc, 0, noticeDesc.length(), mContentBound);

            // 初始化文字Y坐标
            if (mY == 0 && !isInit) {
                mY = getMeasuredHeight() - mTitleBound.top;
                isInit = true;
            }

            // 将文字移动到最上面
            if (mY <= 0 - mTitleBound.bottom) {
                mY = getMeasuredHeight() - mTitleBound.top;
                mIndex++;
                isPaused = false;
            }

            canvas.drawText(noticeDesc, 0, noticeDesc.length(), (mTitleBound.right - mTitleBound.left) + 20 + getTotalPaddingLeft(), mY, mContentPaint);
            canvas.drawText(noticeTitle, 0, noticeTitle.length(), 10 + getTotalPaddingLeft(), mY, mTitlePaint);

            // 将文字移动到中间
            if (!isPaused && mY <= getMeasuredHeight() / 2 - (mTitleBound.top + mTitleBound.bottom) / 2) {
                isMove = false;
                isPaused = true;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        postInvalidate();
                        isMove = true;
                    }
                }, mInterval);
            }

            mY -= mSpeed;

            // 循环使用数据
            if (mIndex == mRotationNoticeList.size()) {
                mIndex = 0;
            }

            // 如果是处于移动状态则会延迟绘制
            // 计算多少毫秒来移动1像素
            if (isMove) {
                postInvalidateDelayed(2);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(mRotationNoticeList.get(mIndex).getNoticeId());
                }
                break;
        }

        return true;
    }

    /**
     * 获取自定义控件属性
     *
     * @param attrs 控件属性值
     */
    private void obtainStyledAttributes(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.VerticalScrollTextView);
        mSpeed = array.getInteger(R.styleable.VerticalScrollTextView_text_view_scroll_speed, 1);
        mInterval = array.getInteger(R.styleable.VerticalScrollTextView_text_view_interval, 1000);
        mTitleColor = array.getColor(R.styleable.VerticalScrollTextView_text_view_title_color, Color.RED);
        mContentColor = array.getColor(R.styleable.VerticalScrollTextView_text_view_content_color, Color.BLACK);
        mTitleSize = (int) array.getDimension(R.styleable.VerticalScrollTextView_text_view_title_size, SizeConvertUtils.Sp2Px(getContext(), 15));
        mContentSize = (int) array.getDimension(R.styleable.VerticalScrollTextView_text_view_content_size, SizeConvertUtils.Sp2Px(getContext(), 15));
        array.recycle();
    }

    /**
     * 初始化Paint默认值
     */
    private void initPaint() {
        // 初始化数据源下标
        mIndex = 0;
        // 初始化标题画笔
        mTitlePaint = new Paint();
        mTitlePaint.setAntiAlias(true);
        mTitlePaint.setDither(true);
        mTitlePaint.setColor(mTitleColor);
        mTitlePaint.setTextSize(mTitleSize);
        mTitleBound = new Rect();
        // 初始化内容画笔
        mContentPaint = new Paint();
        mContentPaint.setAntiAlias(true);
        mContentPaint.setDither(true);
        mContentPaint.setColor(mContentColor);
        mContentPaint.setTextSize(mContentSize);
        mContentBound = new Rect();
    }

    /**
     * 测量文字宽度
     */
    private int measureTextWidth(int widthMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            // 设置标题宽度至少为2个字符宽度
            String words = "半暖";
            Rect rect = new Rect();
            mContentPaint.getTextBounds(words, 0, words.length(), rect);
            result = rect.right - rect.left;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    /**
     * 测量文字高度
     */
    private int measureTextHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            // 设置最小高度为两倍字符高度
            int titleHeight = (int) (mTitlePaint.descent() - mTitlePaint.ascent());
            int contentHeight = (int) (mContentPaint.descent() - mContentPaint.ascent());
            result = Math.max(titleHeight, contentHeight) * 2;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    /**
     * Getter Setter方法
     */
    public void setRotationNoticeList(List<RotationNotice> rotationNoticeList) {
        mRotationNoticeList = rotationNoticeList;
    }

    public void setSpeed(Integer speed) {
        mSpeed = speed;
    }

    public void setInterval(Integer interval) {
        mInterval = interval;
    }

    public void setTitleColor(Integer titleColor) {
        mTitleColor = titleColor;
    }

    public void setContentColor(Integer contentColor) {
        mContentColor = contentColor;
    }

    public void setTitleSize(Integer titleSize) {
        mTitleSize = titleSize;
    }

    public void setContentSize(Integer contentSize) {
        mContentSize = contentSize;
    }
}
