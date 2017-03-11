package app.semiwarm.cn.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 一般ViewPagerAdapter
 * Created by alibct on 2016/12/22.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<ImageView> mImageViewList;

    public ViewPagerAdapter(List<ImageView> imageViewList) {
        this.mImageViewList = imageViewList;
    }

    @Override
    public int getCount() {
        return mImageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = mImageViewList.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
