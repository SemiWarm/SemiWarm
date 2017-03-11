package app.semiwarm.cn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 主页中每一个分类所对应的Fragment
 * Created by alibct on 2016/12/22.
 */

public class HomeFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public HomeFragmentAdapter(FragmentManager manager,List<Fragment> fragmentList) {
        super(manager);
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * 如果需要显示每个Tab下面的标题必须重写这个方法
     * 否则会导致Tab下面不现实标题的Bug
     * @param position Tab的位置
     * @return Tab的标题
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
