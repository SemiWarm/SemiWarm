package app.semiwarm.cn.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * DrawableUtils
 * Created by alibct on 2017/5/24.
 */

public class DrawableUtils {

    public static GradientDrawable getDrawable(int rgb, float radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(radius); //设置圆角的半径
        gradientDrawable.setStroke(3, rgb); //描边
        return gradientDrawable;
    }

    public static StateListDrawable getSelector(Drawable normalDrawable, Drawable checkedDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        // 给当前的颜色选择器添加选中图片指向状态，未选中图片指向状态
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}, checkedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        // 设置默认状态
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }
}
