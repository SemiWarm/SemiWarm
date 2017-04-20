package app.semiwarm.cn.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.semiwarm.cn.R;
import app.semiwarm.cn.entity.Category;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SortItemDeatilActivity extends AppCompatActivity {

    // 准备控件
    @BindView(R.id.tb_title)
    Toolbar mToolbar;
    @BindView(R.id.iv_back_button)
    ImageView mBackImageView;
    @BindView(R.id.tv_tool_bar_title)
    TextView mToolBarTitleTextView;
    @BindView(R.id.iv_search_button)
    ImageView mSearchImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_item_deatil);

        // 设置深色状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }

        // 绑定控件，否则会出现空指针异常
        ButterKnife.bind(this);
        Category category = (Category) getIntent().getSerializableExtra("Category");
        Log.i("Category", category.toString());
        mToolBarTitleTextView.setText(category.getCategoryName());

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSearchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SortItemDeatilActivity.this,SearchActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
