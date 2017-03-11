package app.semiwarm.cn.activity;

import android.content.Intent;
import android.os.Bundle;

import app.semiwarm.cn.R;

public class SplashDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_detail);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SplashDetailActivity.this,MainActivity.class));
        finish();
    }
}
