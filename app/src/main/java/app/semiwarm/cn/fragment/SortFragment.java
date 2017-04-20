package app.semiwarm.cn.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.adapter.FragmentAdapter;
import app.semiwarm.cn.entity.Category;
import app.semiwarm.cn.view.VerticalSlideViewPager;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分类
 * A simple {@link Fragment} subclass.
 */
public class SortFragment extends Fragment {

    private List<Category> mCategoryList;
    private List<RadioButton> mRadioButtonList;
    private int mIndicatorDistance;
    private final int mInitMargin = 65;

    @BindColor(R.color.grey_900)
    int mColorNormal;
    @BindColor(R.color.red_700)
    int mColorSelected;

    @BindView(R.id.rg_sort_titles)
    RadioGroup mSortTitle;
    @BindView(R.id.vp_sort_main)
    VerticalSlideViewPager mSortPagerContainer;
    @BindView(R.id.iv_sort_indicator)
    ImageView mIndicator;

    public SortFragment() {
        // Required empty public constructor
        EventBus.getDefault().register(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        ButterKnife.bind(this, view);

        // ViewPager滑动事件
        mSortPagerContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int initTopMargin = (mSortTitle.getChildAt(0).getHeight() - mIndicator.getHeight()) / 2 + mInitMargin;
                int topMargin = (int) (position * mIndicatorDistance + positionOffset * mIndicatorDistance);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mIndicator.getLayoutParams();
                layoutParams.topMargin = topMargin + initTopMargin;
                mIndicator.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                mRadioButtonList.get(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 设置选中是对应的页面切换事件
        mSortTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mSortPagerContainer.setCurrentItem(i, false);
                for (int j = 0; j < mRadioButtonList.size(); j++) {
                    if (j == i) {
                        mRadioButtonList.get(j).setTextColor(mColorSelected);
                    } else {
                        mRadioButtonList.get(j).setTextColor(mColorNormal);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(List<Category> categoryList) {
        mCategoryList = new ArrayList<>(categoryList);
        initView();
        // 获取上下距离
        mIndicator.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mIndicator.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mIndicatorDistance = mSortTitle.getChildAt(1).getTop() - mSortTitle.getChildAt(0).getTop();
            }
        });
    }

    private void initView() {
        List<Fragment> fragmentList = new ArrayList<>();
        mRadioButtonList = new ArrayList<>();
        Fragment fragment;
        RadioButton radioButton;
        for (int i = 0; i < mCategoryList.size(); i++) {
            fragment = new SortItemFragment();
            // 为每个fragment分发数据
            Bundle bundle = new Bundle();
            bundle.putSerializable("Category", mCategoryList.get(i));
            fragment.setArguments(bundle);
            // 初始化radioButton
            radioButton = new RadioButton(getContext());

            // 设置radioButton的属性
            radioButton.setId(i);
            radioButton.setTextSize(13f);
            radioButton.setText(mCategoryList.get(i).getCategoryName());
            radioButton.setTextColor(mColorNormal);
            // 隐藏radioButton前面的小圆圈
            radioButton.setButtonDrawable(android.R.color.transparent);
            // 设置radioButton布局
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.setMargins(0, mInitMargin, 0, 0);
            radioButton.setLayoutParams(layoutParams);

            fragmentList.add(fragment);
            mRadioButtonList.add(radioButton);

            // 将radioButton添加到容器中
            mSortTitle.addView(radioButton, i);

            // 初始化选中项
            mRadioButtonList.get(0).setChecked(true);
            mRadioButtonList.get(0).setTextColor(mColorSelected);

            // 设置适配器
            mSortPagerContainer.setAdapter(new FragmentAdapter(getActivity().getSupportFragmentManager(), fragmentList));
            mSortPagerContainer.setOffscreenPageLimit(fragmentList.size() - 1);

            // 设置滑动模式
            mSortPagerContainer.setPageTransformer(false, new VerticalSlideViewPager.VerticalTransformer());
            mSortPagerContainer.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

}
