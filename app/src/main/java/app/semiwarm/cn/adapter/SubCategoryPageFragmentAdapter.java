package app.semiwarm.cn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import app.semiwarm.cn.entity.SubCategory;

/**
 * 子类目Fragment的适配器
 * Created by alibct on 2016/12/26.
 */

public class SubCategoryPageFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    private List<SubCategory> mSubCategoryList;

    public SubCategoryPageFragmentAdapter(FragmentManager manager, List<Fragment> fragmentList, List<SubCategory> subCategoryList) {
        super(manager);
        this.mFragmentList = fragmentList;
        this.mSubCategoryList = subCategoryList;
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
        return mSubCategoryList.get(position).getSubCategoryName();
    }
}
