package app.semiwarm.cn.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import app.semiwarm.cn.R;
import app.semiwarm.cn.entity.Category;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 类目页详情
 * A simple {@link Fragment} subclass.
 */
public class SortItemFragment extends Fragment {

    @BindView(R.id.ll_container)
    LinearLayout mContainerLinearLayout;
    @BindView(R.id.rl_banner_container)
    RelativeLayout mBannerContainerRelativeLayout;
    @BindView(R.id.tv_category_title)
    TextView mCategoryTitleTextView;
    @BindColor(R.color.white)
    int mColor;

    private int mContainerWidth;

    public SortItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort_item, container, false);
        ButterKnife.bind(this, view);

        mContainerLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mContainerLinearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mContainerWidth = mContainerLinearLayout.getMeasuredWidth();
                Category category = (Category) getArguments().getSerializable("Category");
                if (category != null) {
                    // 根据容器宽度计算图片宽度
                    int categoryBannerImageWidth = mContainerWidth * 14 / 15;
                    // 根据图片宽度计算图片高度
                    int categoryBannerImageHeight = categoryBannerImageWidth * 5 / 16;
                    // 初始化图片
                    ImageView categoryBannerImageView = new ImageView(getContext());
                    // 加载图片
                    Glide
                            .with(getContext())
                            .load(category.getCategoryBanner())
                            .bitmapTransform(new RoundedCornersTransformation(getContext(), 10, 0))
                            .into(categoryBannerImageView);
                    // 设置布局
                    RelativeLayout.LayoutParams categoryBannerLayoutParams = new RelativeLayout.LayoutParams(categoryBannerImageWidth, categoryBannerImageHeight);
                    categoryBannerImageView.setLayoutParams(categoryBannerLayoutParams);
                    // 添加到布局
                    mBannerContainerRelativeLayout.addView(categoryBannerImageView);

                    // 初始化遮罩图片
                    ImageView coverBannerImageView = new ImageView(getContext());
                    coverBannerImageView.setBackground(getContext().getDrawable(R.drawable.bg_cover_image));
                    // 设置遮罩布局
                    coverBannerImageView.setLayoutParams(categoryBannerLayoutParams);
                    // 添加到布局
                    mBannerContainerRelativeLayout.addView(coverBannerImageView);

                    // 初始化描述TextView
                    TextView categoryDescTextView = new TextView(getContext());
                    categoryDescTextView.setText(category.getCategoryDesc());
                    categoryDescTextView.setTextColor(mColor);
                    categoryDescTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

                    // 设置TextView布局参数
                    RelativeLayout.LayoutParams categoryDescLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    categoryDescLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                    categoryDescTextView.setLayoutParams(categoryDescLayoutParams);
                    // 添加到布局
                    mBannerContainerRelativeLayout.addView(categoryDescTextView);
                    // 加载标题
                    mCategoryTitleTextView.setText(category.getCategoryTitle());
                }
            }
        });
        return view;
    }
}
