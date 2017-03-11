package app.semiwarm.cn.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import app.semiwarm.cn.R;
import butterknife.BindString;

public class BaseActivity extends AppCompatActivity {

    // 上一次按返回键的时间
    private long mLastPressKeyBackTime;
    // 绑定数据
    @BindString(R.string.press_key_back_info)
    String mPressKeyBackInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        // 设置深色状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 按返回键退出应用
     * 防止用户误触
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 首先判断用户是否按了返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 计算最近一次按返回键的时间差
            if ((System.currentTimeMillis() - mLastPressKeyBackTime) > 2000) {
                // 如果大于2秒则认为是用户误操作
                Toast.makeText(this, mPressKeyBackInfo, Toast.LENGTH_SHORT).show();
                // 记录当前时间作为上一次按下返回键的时间
                mLastPressKeyBackTime = System.currentTimeMillis();
            } else {
                // 否则退出应用
                // 这里看情况遍历所有的Activity进行逐一关闭
                System.exit(0);
            }
            // 一定不要忘了返回值
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
