package app.semiwarm.cn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import app.semiwarm.cn.entity.Category;

/**
 * 分类Fragment的适配器
 * Created by alibct on 2016/12/26.
 */

public class SortPageFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    private List<Category> mCategoryList;

    public SortPageFragmentAdapter(FragmentManager manager, List<Fragment> fragmentList, List<Category> categoryList) {
        super(manager);
        this.mFragmentList = fragmentList;
        this.mCategoryList = categoryList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCategoryList.get(position).getCategoryName();
    }
}
