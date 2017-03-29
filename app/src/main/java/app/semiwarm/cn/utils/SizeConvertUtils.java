package app.semiwarm.cn.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * 尺寸转换工具
 * Created by alibct on 2017/3/29.
 */

public class SizeConvertUtils {
    /**
     * Dpi转Px
     *
     * @param context 上下文
     * @param dpi     要转换的dpi尺寸
     * @return px 转换后的px尺寸
     */
    public static int Dpi2Px(Context context, int dpi) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, context.getResources().getDisplayMetrics());
    }

    /**
     * Px转Dpi
     *
     * @param context 上下文
     * @param px      要转换的px尺寸
     * @return dpi 转换后的dpi尺寸
     */
    public static int Px2Dpi(Context context, int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, context.getResources().getDisplayMetrics());
    }

    /**
     * Sp转Px
     *
     * @param context 上下文
     * @param sp      要转换的sp尺寸
     * @return px 转换后的px尺寸
     */
    public static int Sp2Px(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    /**
     * Px转Sp
     *
     * @param context 上下文
     * @param px      要转换的px尺寸
     * @return sp 转换后的sp尺寸
     */
    public static int Px2Sp(Context context, int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, context.getResources().getDisplayMetrics());
    }
}
