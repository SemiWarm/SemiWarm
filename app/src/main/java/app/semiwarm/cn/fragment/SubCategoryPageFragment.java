package app.semiwarm.cn.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import app.semiwarm.cn.R;
import app.semiwarm.cn.entity.SubCategory;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryPageFragment extends Fragment {

    @BindView(R.id.rl_banner_container)
    RelativeLayout mBannerContainerRelativeLayout;
    @BindColor(R.color.white)
    int mColor;
    private SubCategory mSubCategory;
    public SubCategoryPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_category_page, container, false);
        ButterKnife.bind(this, view);
        mSubCategory = (SubCategory) getArguments().getSerializable("SubCategory");
        // 初始化子类目banner
        initSubCategoryBanner();

        return view;
    }

    private void initSubCategoryBanner() {
        // 根据屏幕宽度确定图片宽度
        int width = getScreenWidth(getContext()) * 16 / 17;
        // 根据图片分辨率和图片宽度确定图片高度
        int height = width * 15 / 32;
        // 初始化图片
        ImageView subCategoryBannerImageView = new ImageView(getContext());
        // 加载图片
        Glide
                .with(getContext())
                .load(mSubCategory.getSubCategoryBanner())
                .bitmapTransform(new RoundedCornersTransformation(getContext(), 10, 0))
                .into(subCategoryBannerImageView);
        // 设置布局
        RelativeLayout.LayoutParams subCategoryBannerLayoutParams = new RelativeLayout.LayoutParams(width, height);
        subCategoryBannerImageView.setLayoutParams(subCategoryBannerLayoutParams);
        // 添加到布局
        mBannerContainerRelativeLayout.addView(subCategoryBannerImageView);
        // 初始化遮罩图片
        ImageView coverBannerImageView = new ImageView(getContext());
        coverBannerImageView.setBackground(getContext().getDrawable(R.drawable.bg_cover_image));
        // 设置遮罩布局
        coverBannerImageView.setLayoutParams(subCategoryBannerLayoutParams);
        // 添加到布局
        mBannerContainerRelativeLayout.addView(coverBannerImageView);
        // 初始化描述TextView
        TextView subCategoryDescTextView = new TextView(getContext());
        subCategoryDescTextView.setText(mSubCategory.getSubCategoryDesc());
        subCategoryDescTextView.setTextColor(mColor);
        subCategoryDescTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        // 设置TextView布局参数
        RelativeLayout.LayoutParams subCategoryDescLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        subCategoryDescLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        subCategoryDescTextView.setLayoutParams(subCategoryDescLayoutParams);
        // 添加到布局
        mBannerContainerRelativeLayout.addView(subCategoryDescTextView);
    }

    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    private int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

}
