package app.semiwarm.cn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import app.semiwarm.cn.R;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    // 绑定资源
    @BindColor(R.color.red_900)
    int skipButtonTextColor;
    // 绑定控件
    @BindView(R.id.iv_splash)
    ImageView mSplashImage;
    @BindView(R.id.tv_skip_button)
    TextView mSkipButton;
    private int mSplashDuration = 3; // 广告显示时长
    private Handler mHandler = new Handler();
    // 在线程里更新UI
    private Runnable mCountDownRunnable = new Runnable() {
        @Override
        public void run() {
            if (mSplashDuration > 0) {
                // 为字符串着色
                SpannableStringBuilder spannableString = new SpannableStringBuilder("跳过 ");
                spannableString.append(String.valueOf(mSplashDuration--));
                spannableString.setSpan(new ForegroundColorSpan(skipButtonTextColor), spannableString.length() - 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                mSkipButton.setText(spannableString);
                // 延迟1秒
                mHandler.postDelayed(this, 1000);
            } else {
                startGuideActivity();
            }
        }
    };

    private void startMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void startAdInfoActivity() {
        Intent intent = new Intent(SplashActivity.this, SplashDetailActivity.class);
        startActivity(intent);
        finish();
    }

    private void startGuideActivity() {
        Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 绑定Activity
        ButterKnife.bind(this);
        // 使用第三方图片加载器加载大图防止OOM
        Glide
                .with(this)
                .load(R.drawable.your_name)
                .into(mSplashImage);
        // 点击广告后跳至广告界面，同时移除倒计时线程
        mSplashImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 关闭广告界面或者在广告界面按返回键时启动主界面
                startAdInfoActivity();
                mHandler.removeCallbacks(mCountDownRunnable);
            }
        });
        // 点击跳过按钮后跳至主界面，同时移除倒计时线程
        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(mCountDownRunnable);
                startGuideActivity();
            }
        });
        // 启动线程
        mHandler.postDelayed(mCountDownRunnable, 1000);
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
