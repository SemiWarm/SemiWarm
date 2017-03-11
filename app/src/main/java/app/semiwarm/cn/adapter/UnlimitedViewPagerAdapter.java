package app.semiwarm.cn.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 伪无限轮播适配器
 * Created by alibct on 2016/12/26.
 */

public class UnlimitedViewPagerAdapter extends PagerAdapter {

    private List<ImageView> mImageViewList;

    public UnlimitedViewPagerAdapter(List<ImageView> imageViewList) {
        this.mImageViewList = imageViewList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position % mImageViewList.size();
        ImageView imageView = mImageViewList.get(realPosition);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
