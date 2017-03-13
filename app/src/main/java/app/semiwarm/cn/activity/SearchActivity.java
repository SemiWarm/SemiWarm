package app.semiwarm.cn.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import app.semiwarm.cn.R;
import app.semiwarm.cn.utils.EditTextUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.iv_close_button)
    ImageView mCloseImageView;
    @BindView(R.id.et_search)
    EditText mSearchEditText;
    @BindView(R.id.iv_search_clear)
    ImageView mSearchClearImageView;
    @BindView(R.id.iv_search_button)
    ImageView mSearchImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 设置深色状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        ButterKnife.bind(this);

        mCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        EditTextUtils.addClearListener(mSearchEditText,mSearchClearImageView);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
