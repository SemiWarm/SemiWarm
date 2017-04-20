package app.semiwarm.cn.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import app.semiwarm.cn.R;
import app.semiwarm.cn.entity.Category;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分类页面
 * A simple {@link Fragment} subclass.
 */
public class SortPageFragment extends Fragment {

    @BindView(R.id.ll_sort_page_container)
    LinearLayout mSortPageContainerLinearLayout;

    public SortPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort_page, container, false);
        ButterKnife.bind(this, view);
        ImageView categoryBannerImage;
        // 获取类目信息
        Category category = (Category) getArguments().getSerializable("Category");
        if (category != null) {
            categoryBannerImage = new ImageView(getContext());
            Glide
                    .with(this)
                    .load(category.getCategoryBanner())
                    .into(categoryBannerImage);
            // 根据图片宽高自适应屏幕大小
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            int height = width * 5 / 16; // 图片宽高比为16:5
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
            categoryBannerImage.setLayoutParams(layoutParams);
            mSortPageContainerLinearLayout.addView(categoryBannerImage);
        }
        return view;
    }
}
