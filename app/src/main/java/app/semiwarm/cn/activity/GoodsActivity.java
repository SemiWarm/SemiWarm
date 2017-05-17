package app.semiwarm.cn.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.semiwarm.cn.R;
import app.semiwarm.cn.view.VerticalSlideViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.iv_back_button)
    ImageView mBtnBackImageView;
    @BindView(R.id.tv_tool_bar_title)
    TextView mToolBarTitleTextView;
    @BindView(R.id.iv_home)
    ImageView mBtnBackHomeImageView;
    @BindView(R.id.vp_goods)
    VerticalSlideViewPager mGoodsViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);
        // 设置深色状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        // 首先需要获取传过来的商品id
        // 请求商品数据

        mBtnBackImageView.setOnClickListener(this);
        mBtnBackHomeImageView.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_button:
                finish();
                break;
            case R.id.iv_home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }
}
