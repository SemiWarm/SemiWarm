package app.semiwarm.cn.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.adapter.SubCategoryPageFragmentAdapter;
import app.semiwarm.cn.entity.Category;
import app.semiwarm.cn.entity.SubCategory;
import app.semiwarm.cn.fragment.SubCategoryPageFragment;
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.service.observable.SubCategoryServiceObservable;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class SortItemDeatilActivity extends AppCompatActivity {

    // 准备控件
    @BindView(R.id.tb_title)
    Toolbar mToolbar;
    @BindView(R.id.iv_back_button)
    ImageView mBackImageView;
    @BindView(R.id.tv_tool_bar_title)
    TextView mToolBarTitleTextView;
    @BindView(R.id.iv_search_button)
    ImageView mSearchImageView;

    @BindView(R.id.tb_subcategory_tabs)
    TabLayout mSubCategoryTabs;
    @BindView(R.id.vp_subcategory_page_container)
    ViewPager mSubCategoryPageContainer;

    private List<SubCategory> mSubCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_item_deatil);

        // 设置深色状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }

        // 绑定控件，否则会出现空指针异常
        ButterKnife.bind(this);
        Category category = (Category) getIntent().getSerializableExtra("Category");
        Log.i("Category", category.toString());
        // 获取传过来的index
        final int index = getIntent().getIntExtra("index", -1);
        mToolBarTitleTextView.setText(category.getCategoryName());
        // 进行数据请求
        SubCategoryServiceObservable observable = new SubCategoryServiceObservable();
        observable.getSubCategoriesByCategoryId(category.getCategoryId())
                .subscribe(new Subscriber<BaseResponse<List<SubCategory>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<SubCategory>> listBaseResponse) {
                        // 得到数据后进行tab初始化
                        if (listBaseResponse.getSuccess() == 1) {
                            mSubCategories = new ArrayList<>(listBaseResponse.getData());
                            initTabsAndFragments(mSubCategories, index);
                        }
                    }
                });

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSearchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SortItemDeatilActivity.this, SearchActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initTabsAndFragments(List<SubCategory> subCategories, int index) {
        List<Fragment> fragments = new ArrayList<>();
        Fragment fragment;
        for (int i = 0; i < subCategories.size(); i++) {
            fragment = new SubCategoryPageFragment();
            // 为每一个Fragement设置SubCategory信息
            Bundle bundle = new Bundle();
            bundle.putSerializable("SubCategory", subCategories.get(i));
            fragment.setArguments(bundle);
            // 添加到容器进行统一管理
            fragments.add(fragment);
            mSubCategoryTabs.addTab(mSubCategoryTabs.newTab().setText(subCategories.get(i).getSubCategoryName()), i);
        }

        // 设置适配器
        mSubCategoryPageContainer.setAdapter(new SubCategoryPageFragmentAdapter(getSupportFragmentManager(), fragments, subCategories));
        // 设置最大缓存
        mSubCategoryPageContainer.setOffscreenPageLimit(fragments.size() - 1);
        // Tabs关联ViewPager
        mSubCategoryTabs.setupWithViewPager(mSubCategoryPageContainer);
        // 设置被选中的子类目
        if (index != -1) {
            mSubCategoryPageContainer.setCurrentItem(index);
        }
    }
}
