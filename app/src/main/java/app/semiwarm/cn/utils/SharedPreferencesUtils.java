package app.semiwarm.cn.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

/**
 * 用于处理所有关于SharedPreferences的操作
 * Created by alibct on 2017/2/26.
 */

public class SharedPreferencesUtils {
    /**
     * 检查用户是否为第一次启动应用
     *
     * @param context 应用上下文
     * @return 布尔值
     */
    public static boolean isFirstStart(Context context) {
        float currentVersionCode = 0;
        // 获取当前应用版本号
        try {
            currentVersionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // 获取已存储的应用版本号
        SharedPreferences spConfig = context.getSharedPreferences("VersionCode", Context.MODE_PRIVATE);
        float storedVersionCode = spConfig.getFloat("StoredVersionCode", 0);
        // 如果当前版本号大于已存储的版本号则表示应用已经更新，判定为第一次启动
        if (currentVersionCode > storedVersionCode) {
            // 存储当前版本号为新的已存储版本号
            spConfig.edit().putFloat("StoredVersionCode", currentVersionCode).apply();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查本地是否存储有广告信息
     *
     * @param context 应用上下文
     * @return 布尔值
     */
    public static boolean isSplashInfoStored(Context context) {
        // 查看本地SharedPreferences文件中是否存储有Splash的信息
        SharedPreferences spSplashInfo = context.getSharedPreferences("SplashInfo",Context.MODE_PRIVATE);
        String splashId = spSplashInfo.getString("SplashId",null);
        return splashId != null;
    }

    public static void getAndStoreSplashInfo(Context context){
        // 通过api从服务器获取最新的SplashInfo信息
    }
}
