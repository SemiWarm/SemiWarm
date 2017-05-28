package app.semiwarm.cn.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.activity.CartActivity;
import app.semiwarm.cn.adapter.SortPageFragmentAdapter;
import app.semiwarm.cn.entity.Category;
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.service.observable.CategoryServiceObservable;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * 主页
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    // 所有类目信息
    private List<Category> mCategoryList;

    @BindView(R.id.tb_sort_tabs)
    TabLayout mSortTabs;

    @BindView(R.id.vp_sort_page_container)
    ViewPager mSortPagerContainer;

    @BindView(R.id.iv_cart_button)
    ImageView mCartImageView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        mCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CartActivity.class));
            }
        });

        // 请求所有类目信息
        CategoryServiceObservable categoryService = new CategoryServiceObservable();
        categoryService.getAllCategories()
                .subscribe(new Subscriber<BaseResponse<List<Category>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<Category>> listBaseResponse) {
                        if (listBaseResponse.getSuccess() == 1) {
                            mCategoryList = new ArrayList<>(listBaseResponse.getData());
                            initTabsAndFragments();
                            EventBus.getDefault().post(mCategoryList);
                        } else {
                            Toast.makeText(getActivity(), "没有可用的分类数据!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return view;
    }

    private void initTabsAndFragments() {
        // 初始化List
        List<Fragment> fragmentList = new ArrayList<>();
        // 初始化第一个Fragment
        Fragment fragment;
        for (int i = 0; i < mCategoryList.size(); i++) {
            // 初始化Tab
            mSortTabs.addTab(mSortTabs.newTab().setText(mCategoryList.get(i).getCategoryName()), i);
            // 初始化IndexFragment
            if (i == 0) {
                fragment = new IndexFragment();
                fragmentList.add(fragment);
            } else {
                // 初始化OtherFragment
                fragment = new SortPageFragment();
                // 为每一个Fragement设置Category信息
                Bundle bundle = new Bundle();
                bundle.putSerializable("Category", mCategoryList.get(i));
                fragment.setArguments(bundle);
                // 添加到容器进行统一管理
                fragmentList.add(fragment);
            }
        }
        // 设置适配器
        mSortPagerContainer.setAdapter(new SortPageFragmentAdapter(getActivity().getSupportFragmentManager(), fragmentList, mCategoryList));
        // 设置最大缓存
        mSortPagerContainer.setOffscreenPageLimit(fragmentList.size() - 1);
        // Tabs关联ViewPager
        mSortTabs.setupWithViewPager(mSortPagerContainer);
    }
}
