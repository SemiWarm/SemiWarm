package app.semiwarm.cn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * 欢迎界面需求说明
 * 1、检查用户是否为第一次启动应用
 * 2、如果是第一次启动应用则进入GuideActivity
 * 3、如果不是第一次启动应用则检查本地是否存储有广告信息
 * 3.1、如果本地已经存储有广告信息则进入SplashActivity
 * 3.2、如果本地没有存储有广告信息则从服务器获取最新的广告信息并存储在本地，存储完成后进入SplashActivity
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(WelcomeActivity.this, SplashActivity.class));
        finish();
//        if (SharedPreferencesUtils.isFirstStart(this)) {
//            // 第一次启动应用直接进入用户向导页面
//            startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
//            finish();
//        } else {
//            if (SharedPreferencesUtils.isSplashInfoStored(this)){
//                // 已经存储有广告信息，直接进入SplashActivity
//                startActivity(new Intent(WelcomeActivity.this,SplashActivity.class));
//                finish();
//            } else {
//                // 从服务起获取最新的广告信息并存储在本地的SharedPreferences中
//            }
//        }
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
