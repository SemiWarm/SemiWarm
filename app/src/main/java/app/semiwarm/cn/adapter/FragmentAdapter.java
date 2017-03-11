package app.semiwarm.cn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Fragment适配器
 * Created by alibct on 2016/12/22.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public FragmentAdapter(FragmentManager manager, List<Fragment> fragmentList) {
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
}
