package app.semiwarm.cn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.adapter.ViewPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends BaseActivity implements View.OnClickListener {

    // 准备引导图资源
    private int[] mImageResId = new int[]{R.drawable.guide20170310, R.drawable.guide20170320, R.drawable.guide20170330};
    // 准备ImageView数组
    private List<ImageView> mImageViewList;
    // 准备小圆点之间的距离
    private int mDotsDistance;

    // 准备控件
    @BindView(R.id.vp_guide)
    ViewPager mGuideViewPager;
    @BindView(R.id.ll_dots)
    LinearLayout mDotsLayout;
    @BindView(R.id.iv_dots)
    ImageView mDotsImageView;

    // 准备按钮
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        // 绑定Activity,否则会出现空指针异常
        ButterKnife.bind(this);
        initImageViewAndDots();

        ViewPagerAdapter adapter = new ViewPagerAdapter(mImageViewList);
        mGuideViewPager.setAdapter(adapter);

        // 获取移动的小圆点之间的距离
        mDotsImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mDotsImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mDotsDistance = mDotsLayout.getChildAt(1).getLeft() - mDotsLayout.getChildAt(0).getLeft();
            }
        });

        // 设置ViewPager的移动监听
        mGuideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int leftMargin = (int) (position * mDotsDistance + positionOffset * mDotsDistance);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mDotsImageView.getLayoutParams();
                layoutParams.leftMargin = leftMargin;
                mDotsImageView.setLayoutParams(layoutParams);

                if (mImageViewList.size() - 1 == position) {
                    btnSignIn.setVisibility(View.VISIBLE);
                    btnSignUp.setVisibility(View.VISIBLE);
                    btnSignIn.setOnClickListener(GuideActivity.this);
                    btnSignUp.setOnClickListener(GuideActivity.this);
                } else {
                    btnSignIn.setVisibility(View.INVISIBLE);
                    btnSignUp.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initImageViewAndDots() {
        mImageViewList = new ArrayList<>();
        ImageView imageView;
        ImageView dots;
        for (int i = 0; i < mImageResId.length; i++) {
            imageView = new ImageView(this);
            Glide
                    .with(this)
                    .load(mImageResId[i])
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageViewList.add(imageView);

            dots = new ImageView(this);
            dots.setImageResource(R.drawable.bg_guide_dots_normal);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                layoutParams.setMarginStart(20);
            }
            dots.setLayoutParams(layoutParams);
            mDotsLayout.addView(dots);
        }
    }

    @Override
    public void onClick(View view) {
        if (R.id.btn_sign_in == view.getId()) {
            startActivity(new Intent(GuideActivity.this, SignInActivity.class));
            finish();
        }
        if (R.id.btn_sign_up == view.getId()) {
            startActivity(new Intent(GuideActivity.this, SignUpActivity.class));
            finish();
        }
    }
}
