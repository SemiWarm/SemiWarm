package app.semiwarm.cn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import app.semiwarm.cn.utils.SharedPreferencesUtils;

/**
 * 欢迎界面需求说明
 * 1、检查当前网络是否可用
 * 如果网络不可用直接打开MainActivity并提示网络不可用
 * 2、如果网络可用，检查用户是否为第一次打开应用
 * 如果是第一次打开应用则直接打开GuideActivity
 * 3、如果不是第一次打开应用就从服务器获取最新的Splash信息
 * 并将服务器的Splash图片缓存到内存中
 * 打开SplashActivity并传递Splash信息
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPreferencesUtils.isFirstStart(this)) {
            // 第一次启动应用直接进入用户向导页面
            startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
            finish();
        } else {
            // 从服务器获取最新的Splash信息
            startActivity(new Intent(WelcomeActivity.this, SplashActivity.class));
            finish();
        }
    }

    // 屏蔽返回键和菜单键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
