package app.semiwarm.cn.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.activity.GoodsActivity;
import app.semiwarm.cn.adapter.SearchGoodsAdapter;
import app.semiwarm.cn.entity.Category;
import app.semiwarm.cn.entity.Goods;
import app.semiwarm.cn.entity.SubCategory;
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.service.observable.GoodsServiceObservable;
import app.semiwarm.cn.service.observable.SubCategoryServiceObservable;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * 分类页面
 * A simple {@link Fragment} subclass.
 */
public class SortPageFragment extends Fragment {

    @BindView(R.id.ll_sort_page_container)
    LinearLayout mSortPageContainerLinearLayout;

    public SortPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort_page, container, false);
        ButterKnife.bind(this, view);
        ImageView categoryBannerImage;
        // 获取类目信息
        Category category = (Category) getArguments().getSerializable("Category");
        if (category != null) {
            categoryBannerImage = new ImageView(getContext());
            Glide
                    .with(this)
                    .load(category.getCategoryBanner())
                    .into(categoryBannerImage);
            // 根据图片宽高自适应屏幕大小
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            int height = width * 5 / 16; // 图片宽高比为16:5
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
            categoryBannerImage.setLayoutParams(layoutParams);
            mSortPageContainerLinearLayout.addView(categoryBannerImage);
            initSubCategoryGoods(category.getCategoryId());
        }
        return view;
    }

    public void initSubCategoryGoods(Integer categoryId) {
        // 取该类目下的所有子类目信息
        SubCategoryServiceObservable subCategoryService = new SubCategoryServiceObservable();
        subCategoryService.getSubCategoriesByCategoryId(categoryId).subscribe(new Subscriber<BaseResponse<List<SubCategory>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<List<SubCategory>> listBaseResponse) {
                if (listBaseResponse.getSuccess() == 1) {
                    Log.i("subCategoryList:", listBaseResponse.getData().toString());
                    // 根据每一个子类目信息先布局子类目名称和描述
                    for (SubCategory subCategory : listBaseResponse.getData()) {
                        // 初始化标题和描述
                        TextView subCategoryTitle = new TextView(getContext());
                        subCategoryTitle.setText(subCategory.getSubCategoryTitle());
                        subCategoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                        subCategoryTitle.setTextColor(Color.parseColor("#424242"));
                        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        titleLayoutParams.gravity = Gravity.CENTER;
                        titleLayoutParams.setMargins(0, 20, 0, 0);
                        subCategoryTitle.setLayoutParams(titleLayoutParams);
                        mSortPageContainerLinearLayout.addView(subCategoryTitle);
                        TextView subCategoryDesc = new TextView(getContext());
                        subCategoryDesc.setText(subCategory.getSubCategoryDesc());
                        subCategoryDesc.setTextColor(Color.parseColor("#BDBDBD"));
                        LinearLayout.LayoutParams descLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        descLayoutParams.gravity = Gravity.CENTER;
                        subCategoryDesc.setLayoutParams(descLayoutParams);
                        mSortPageContainerLinearLayout.addView(subCategoryDesc);
                        // 根据子类目id获取所有子类目商品
                        GoodsServiceObservable goodsService = new GoodsServiceObservable();
                        goodsService.getAllGoodsBySubCategoryId(subCategory.getSubCategoryId()).subscribe(new Subscriber<BaseResponse<List<Goods>>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(BaseResponse<List<Goods>> listBaseResponse) {
                                if (listBaseResponse.getSuccess() == 1) {
                                    // 初始化所有商品
                                    RecyclerView goodsRecyclerView = new RecyclerView(getContext());
                                    // 初始化RecyclerView
                                    initRecyclerView(goodsRecyclerView, listBaseResponse.getData());
                                    // 初始化布局
                                    LinearLayout.LayoutParams goodsLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    goodsRecyclerView.setLayoutParams(goodsLayoutParams);
                                    mSortPageContainerLinearLayout.addView(goodsRecyclerView);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    public void initRecyclerView(RecyclerView goodsRecyclerView, final List<Goods> goodsList) {
        // 1. 初始化VirtualLayoutManager
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(getContext());
        // 2. 将VirtualLayoutManager绑定到RecyclerView
        goodsRecyclerView.setLayoutManager(layoutManager);
        // 3. 设置回收复用池大小
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        goodsRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        // 4. 设置GridLayoutHelper
        GridLayoutHelper layoutHelper = new GridLayoutHelper(2); // 设置3列
        layoutHelper.setAutoExpand(false); // 是否自动填充空白区
        layoutHelper.setSpanCount(2); // 设置网格个数
        // 5. 设置GridLayoutHelper其它属性
        layoutHelper.setItemCount(goodsList.size()); // 设置布局中item的个数
        // 6. 初始化Adapter
        SearchGoodsAdapter searchGoodsAdapter = new SearchGoodsAdapter(getContext(), layoutHelper, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), goodsList);
        // 7. 设置Adapter
        List<DelegateAdapter.Adapter> adapters = new ArrayList<>();
        adapters.add(searchGoodsAdapter);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);
        goodsRecyclerView.setAdapter(delegateAdapter);
        // 8. 设置点击事件
        searchGoodsAdapter.setSearchGoodsItemClickListener(new SearchGoodsItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Toast.makeText(getContext(), "商品id" + goodsList.get(postion).getGoodsId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), GoodsActivity.class);
                intent.putExtra("goodsId", goodsList.get(postion).getGoodsId());
                startActivity(intent);
            }
        });
    }
}
