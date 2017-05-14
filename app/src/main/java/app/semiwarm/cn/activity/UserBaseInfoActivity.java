package app.semiwarm.cn.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import app.semiwarm.cn.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserBaseInfoActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.iv_back_button)
    ImageView mBtnBackImageView;
    @BindView(R.id.tv_update_user_info)
    TextView mUpdateInfoTextView;
    @BindView(R.id.ci_user_avatar)
    CircleImageView mUserAvatarCircleImageView;

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_base_info);
        ButterKnife.bind(this);
        // 设置深色状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }

        mBtnBackImageView.setOnClickListener(this);
        mUpdateInfoTextView.setOnClickListener(this);

        Glide
                .with(this)
                .load(R.drawable.ic_select_pic)
                .into(mUserAvatarCircleImageView);

        mUserAvatarCircleImageView.setOnClickListener(this);
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
            case R.id.tv_update_user_info:
                Toast.makeText(UserBaseInfoActivity.this, "信息已更新!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ci_user_avatar:
                mIntent = new Intent(this, ImagePickerActivity.class);
                startActivityForResult(mIntent, 100);
                break;
        }
    }
}
