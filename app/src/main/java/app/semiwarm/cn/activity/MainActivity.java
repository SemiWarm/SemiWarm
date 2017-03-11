package app.semiwarm.cn.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.adapter.FragmentAdapter;
import app.semiwarm.cn.fragment.ExploreFragment;
import app.semiwarm.cn.fragment.HomeFragment;
import app.semiwarm.cn.fragment.MineFragment;
import app.semiwarm.cn.fragment.SortFragment;
import app.semiwarm.cn.fragment.TopicsFragment;
import app.semiwarm.cn.view.MotionlessViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    // 底部按钮对应的Fragments
    private List<Fragment> mFragmentList;
    // 绑定控件
    @BindView(R.id.mvp_navigation_pager)
    MotionlessViewPager mNavigationPager;
    @BindView(R.id.rg_navigation_menu)
    RadioGroup mNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 绑定注解
        ButterKnife.bind(this);
        // 初始化mFragmentList
        initFragments();
        // 初始化ViewPager适配器
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mNavigationPager.setOffscreenPageLimit(mFragmentList.size() - 1); // 防止Fragment被销毁
        mNavigationPager.setAdapter(adapter);

        // 添加RadioGroup的点击事件
        mNavigationMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_home) {
                    mNavigationPager.setCurrentItem(0, false);
                } else if (i == R.id.rb_sort) {
                    mNavigationPager.setCurrentItem(1, false);
                } else if (i == R.id.rb_explore) {
                    mNavigationPager.setCurrentItem(2, false);
                } else if (i == R.id.rb_topics) {
                    mNavigationPager.setCurrentItem(3, false);
                } else if (i == R.id.rb_mine) {
                    mNavigationPager.setCurrentItem(4, false);
                }
            }
        });

    }

    private void initFragments() {
        // 初始化Fragments
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new HomeFragment()); // 主页
        mFragmentList.add(new SortFragment()); // 分类
        mFragmentList.add(new ExploreFragment()); // 探索
        mFragmentList.add(new TopicsFragment()); // 专题
        mFragmentList.add(new MineFragment()); // 我的
    }
}
