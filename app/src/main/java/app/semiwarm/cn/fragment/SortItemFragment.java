package app.semiwarm.cn.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.VirtualLayoutManager.LayoutParams;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.activity.SortItemDeatilActivity;
import app.semiwarm.cn.adapter.SubCategoryAdapter;
import app.semiwarm.cn.entity.Category;
import app.semiwarm.cn.entity.SubCategory;
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.service.observable.SubCategoryServiceObservable;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import rx.Subscriber;

/**
 * 类目页详情
 * A simple {@link Fragment} subclass.
 */
public class SortItemFragment extends Fragment {

    @BindView(R.id.ll_container)
    LinearLayout mContainerLinearLayout;
    @BindView(R.id.rl_banner_container)
    RelativeLayout mBannerContainerRelativeLayout;
    @BindView(R.id.tv_category_title)
    TextView mCategoryTitleTextView;
    @BindView(R.id.rv_subcategory)
    RecyclerView mSubCategoryRecyclerView;
    @BindColor(R.color.white)
    int mColor;

    private int mContainerWidth;
    private Category mCategory;
    private List<SubCategory> mSubCategoryList;

    public SortItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort_item, container, false);
        ButterKnife.bind(this, view);
        // 获取通过Intent启动是传入的数据
        mCategory = (Category) getArguments().getSerializable("Category");
        mContainerLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mContainerLinearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                // 获取Banner父布局宽度以计算Banner宽度
                mContainerWidth = mContainerLinearLayout.getMeasuredWidth();
                if (mCategory != null) {
                    Log.i("mCategory", mCategory.toString());
                    // 根据容器宽度计算图片宽度
                    int categoryBannerImageWidth = mContainerWidth * 14 / 15;
                    // 根据图片宽度计算图片高度
                    int categoryBannerImageHeight = categoryBannerImageWidth * 5 / 16;
                    // 初始化图片
                    ImageView categoryBannerImageView = new ImageView(getContext());
                    // 加载图片
                    Glide
                            .with(getContext())
                            .load(mCategory.getCategoryBanner())
                            .bitmapTransform(new RoundedCornersTransformation(getContext(), 10, 0))
                            .into(categoryBannerImageView);
                    // 设置布局
                    RelativeLayout.LayoutParams categoryBannerLayoutParams = new RelativeLayout.LayoutParams(categoryBannerImageWidth, categoryBannerImageHeight);
                    categoryBannerImageView.setLayoutParams(categoryBannerLayoutParams);
                    // 添加到布局
                    mBannerContainerRelativeLayout.addView(categoryBannerImageView);
                    // 初始化遮罩图片
                    ImageView coverBannerImageView = new ImageView(getContext());
                    coverBannerImageView.setBackground(getContext().getDrawable(R.drawable.bg_cover_image));
                    // 设置遮罩布局
                    coverBannerImageView.setLayoutParams(categoryBannerLayoutParams);
                    // 添加到布局
                    mBannerContainerRelativeLayout.addView(coverBannerImageView);
                    // 初始化描述TextView
                    TextView categoryDescTextView = new TextView(getContext());
                    categoryDescTextView.setText(mCategory.getCategoryDesc());
                    categoryDescTextView.setTextColor(mColor);
                    categoryDescTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    // 设置TextView布局参数
                    RelativeLayout.LayoutParams categoryDescLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    categoryDescLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                    categoryDescTextView.setLayoutParams(categoryDescLayoutParams);
                    // 添加到布局
                    mBannerContainerRelativeLayout.addView(categoryDescTextView);
                    // 加载标题
                    mCategoryTitleTextView.setText(mCategory.getCategoryTitle());
                }
            }
        });
        // 类目Banner区域点击事件
        mBannerContainerRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SortItemDeatilActivity.class);
                intent.putExtra("Category", mCategory);
                startActivityForResult(intent, 1);
            }
        });
        // *******************我是分割线---子类目部分开始*******************
        // 进行数据请求
        SubCategoryServiceObservable observable = new SubCategoryServiceObservable();
        observable.getSubCategoriesByCategoryId(mCategory.getCategoryId())
                .subscribe(new Subscriber<BaseResponse<List<SubCategory>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.getCause();
                    }

                    @Override
                    public void onNext(BaseResponse<List<SubCategory>> listBaseResponse) {
                        if (listBaseResponse.getSuccess() == 1) {
                            mSubCategoryList = new ArrayList<>(listBaseResponse.getData());
                            // 1. 初始化VirtualLayoutManager
                            VirtualLayoutManager layoutManager = new VirtualLayoutManager(getContext());
                            // 2. 将VirtualLayoutManager绑定到RecyclerView
                            mSubCategoryRecyclerView.setLayoutManager(layoutManager);
                            // 3. 设置回收复用池大小
                            RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
                            mSubCategoryRecyclerView.setRecycledViewPool(viewPool);
                            viewPool.setMaxRecycledViews(0, 10);
                            // 4. 设置GridLayoutHelper
                            GridLayoutHelper layoutHelper = new GridLayoutHelper(3); // 设置3列
                            layoutHelper.setAutoExpand(false); // 是否自动填充空白区
                            layoutHelper.setSpanCount(3); // 设置网格个数
                            // 5. 设置GridLayoutHelper其它属性
                            layoutHelper.setItemCount(mSubCategoryList.size()); // 设置布局中item的个数
                            // 6. 初始化Adapter
                            SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(getContext(), layoutHelper, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), mSubCategoryList);
                            // 7. 设置Adapter
                            List<DelegateAdapter.Adapter> adapters = new ArrayList<>();
                            adapters.add(subCategoryAdapter);
                            DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
                            delegateAdapter.setAdapters(adapters);
                            mSubCategoryRecyclerView.setAdapter(delegateAdapter);
                            // 8. 设置点击事件
                            subCategoryAdapter.setItemClickListener(new SubCategoryItemClickListener() {
                                @Override
                                public void onItemClick(View view, int postion) {
                                    Toast.makeText(getContext(), "点击位置:" + postion, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
        return view;
    }
}
