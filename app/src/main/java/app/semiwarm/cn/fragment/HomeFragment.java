package app.semiwarm.cn.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.adapter.SortPageFragmentAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    // 类目名称
    private String[] mSortTitles = new String[]{"推荐", "居家", "餐厨", "配件", "服装", "洗护", "婴童", "杂货", "饮食", "其他"};

    // 类目Fragment
    private List<Fragment> mFragmentList;

    @BindView(R.id.tb_sort_tabs)
    TabLayout mSortTabs;

    @BindView(R.id.vp_sort_page_container)
    ViewPager mSortPagerContainer;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        initTabsAndFragments();

        mSortPagerContainer.setAdapter(new SortPageFragmentAdapter(getActivity().getSupportFragmentManager(), mFragmentList, mSortTitles));

        mSortPagerContainer.setOffscreenPageLimit(mFragmentList.size() - 1);

        mSortTabs.setupWithViewPager(mSortPagerContainer);

        return view;
    }

    private void initTabsAndFragments() {
        // 初始化List
        mFragmentList = new ArrayList<>();
        // 初始化第一个Fragment
        Fragment fragment;
        for (int i = 0; i < mSortTitles.length; i++) {
            // 初始化Tab
            mSortTabs.addTab(mSortTabs.newTab().setText(mSortTitles[i]), i);
            // 初始化IndexFragment
            if (i == 0) {
                fragment = new IndexFragment();
                mFragmentList.add(fragment);
            } else {
                // 初始化OtherFragment
                fragment = new SortPageFragment();
                mFragmentList.add(fragment);
            }
        }
    }
}
