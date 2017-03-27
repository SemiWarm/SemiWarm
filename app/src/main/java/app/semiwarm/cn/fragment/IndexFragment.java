package app.semiwarm.cn.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.adapter.UnlimitedViewPagerAdapter;

/**
 * 首页
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends Fragment {

    private int[] mImageResId = new int[]{R.drawable.image01, R.drawable.image02, R.drawable.image03, R.drawable.image04, R.drawable.image05};

    private List<ImageView> mImageViewList;

    private int mDotsDistance;

    private boolean mIsRotating = false;

    private ViewPager mImagesViewPager;

    private LinearLayout mDotsContainer;

    private ImageView mSelectedDots;

    private static final int mInitMargin = 20;

    public IndexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_index, container, false);

        initImagesContainer(view);

        initImageViewAndDots(view);

        mSelectedDots = (ImageView) view.findViewById(R.id.iv_selected_dots);

        mImagesViewPager.setAdapter(new UnlimitedViewPagerAdapter(mImageViewList));

        // 设置当前位置为最大值的中间值，这样就实现了向左是无限循环向右也是无限循环
        int initPosition = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % mImageViewList.size());
        mImagesViewPager.setCurrentItem(initPosition);

        // 获取小圆点之间的距离
        mSelectedDots.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mSelectedDots.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mDotsDistance = mDotsContainer.getChildAt(1).getLeft() - mDotsContainer.getChildAt(0).getLeft();
            }
        });

        mImagesViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int realPosition = position % mImageViewList.size();
                int leftMargin = realPosition * mDotsDistance;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mSelectedDots.getLayoutParams();
                layoutParams.leftMargin = leftMargin;
                mSelectedDots.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 开启轮播
        new Thread() {
            @Override
            public void run() {
                mIsRotating = true;
                while (mIsRotating) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mImagesViewPager.setCurrentItem(mImagesViewPager.getCurrentItem() + 1);
                            }
                        });
                    }
                }
            }
        }.start();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIsRotating) {
            mIsRotating = false;
        }
    }

    private void initImagesContainer(View view) {
        LinearLayout imagesContainer = (LinearLayout) view.findViewById(R.id.ll_images_container);
        mImagesViewPager = new ViewPager(getContext());

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = width * 5 / 12;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,height);
        mImagesViewPager.setLayoutParams(layoutParams);

        imagesContainer.addView(mImagesViewPager);
    }

    private void initImageViewAndDots(View view) {

        mDotsContainer = (LinearLayout) view.findViewById(R.id.ll_dots_container);
        mImageViewList = new ArrayList<>();
        ImageView imageView;
        ImageView dots;
        for (int i = 0; i < mImageResId.length; i++) {
            imageView = new ImageView(getContext());
            Glide
                    .with(this)
                    .load(mImageResId[i])
                    .into(imageView);
            mImageViewList.add(imageView);

            dots = new ImageView(getContext());
            dots.setImageResource(R.drawable.bg_normal_dots);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                layoutParams.setMarginStart(mInitMargin);
            }
            dots.setLayoutParams(layoutParams);
            mDotsContainer.addView(dots);
        }

    }

}
