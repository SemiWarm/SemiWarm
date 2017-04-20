package app.semiwarm.cn.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 子类
 * A simple {@link Fragment} subclass.
 */
public class SortItemFragment extends Fragment {

    @BindView(R.id.ll_container)
    LinearLayout mContainerLinearLayout;
    @BindView(R.id.rl_banner_container)
    RelativeLayout mBannerContainerRelativeLayout;
    @BindView(R.id.tv_category_desc)
    TextView mCategoryDescTextView;
    @BindView(R.id.tv_category_title)
    TextView mCategoryTitleTextView;

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
                    Log.i("width:", "" + categoryBannerImageWidth);
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
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(categoryBannerImageWidth, categoryBannerImageHeight);
                    categoryBannerImageView.setLayoutParams(layoutParams);
                    // 添加到布局
                    mBannerContainerRelativeLayout.addView(categoryBannerImageView);

                    // 加载描述
                    mCategoryDescTextView.setText(category.getCategoryDesc());
                    // 加载标题
                    mCategoryTitleTextView.setText(category.getCategoryTitle());
                }
            }
        });
        return view;
    }
}
