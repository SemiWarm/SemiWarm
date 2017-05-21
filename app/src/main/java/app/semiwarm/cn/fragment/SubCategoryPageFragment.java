package app.semiwarm.cn.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import app.semiwarm.cn.entity.Goods;
import app.semiwarm.cn.entity.SubCategory;
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.service.observable.GoodsServiceObservable;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryPageFragment extends Fragment {

    @BindView(R.id.rl_banner_container)
    RelativeLayout mBannerContainerRelativeLayout;
    @BindView(R.id.rv_goods)
    RecyclerView mGoodsRecyclerView;
    @BindColor(R.color.white)
    int mColor;
    private SubCategory mSubCategory;

    public SubCategoryPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_category_page, container, false);
        ButterKnife.bind(this, view);
        mSubCategory = (SubCategory) getArguments().getSerializable("SubCategory");
        // 初始化子类目banner
        initSubCategoryBanner();
        // 开启网络请求
        if (null != mSubCategory) {
            Log.i("SubCategoryId:", mSubCategory.getSubCategoryId().toString());
            GoodsServiceObservable goodsService = new GoodsServiceObservable();
            goodsService.getAllGoodsBySubCategoryId(mSubCategory.getSubCategoryId()).subscribe(new Subscriber<BaseResponse<List<Goods>>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(BaseResponse<List<Goods>> listBaseResponse) {
                    if (listBaseResponse.getSuccess() == 1) {
                        initRecyclerView(listBaseResponse.getData());
                    }
                }
            });
        }

        return view;
    }

    private void initSubCategoryBanner() {
        // 根据屏幕宽度确定图片宽度
        int width = getScreenWidth(getContext()) * 16 / 17;
        // 根据图片分辨率和图片宽度确定图片高度
        int height = width * 15 / 32;
        // 初始化图片
        ImageView subCategoryBannerImageView = new ImageView(getContext());
        // 加载图片
        Glide
                .with(getContext())
                .load(mSubCategory.getSubCategoryBanner())
                .bitmapTransform(new RoundedCornersTransformation(getContext(), 10, 0))
                .into(subCategoryBannerImageView);
        // 设置布局
        RelativeLayout.LayoutParams subCategoryBannerLayoutParams = new RelativeLayout.LayoutParams(width, height);
        subCategoryBannerImageView.setLayoutParams(subCategoryBannerLayoutParams);
        // 添加到布局
        mBannerContainerRelativeLayout.addView(subCategoryBannerImageView);
        // 初始化遮罩图片
        ImageView coverBannerImageView = new ImageView(getContext());
        coverBannerImageView.setBackground(getContext().getDrawable(R.drawable.bg_cover_image));
        // 设置遮罩布局
        coverBannerImageView.setLayoutParams(subCategoryBannerLayoutParams);
        // 添加到布局
        mBannerContainerRelativeLayout.addView(coverBannerImageView);
        // 初始化描述TextView
        TextView subCategoryDescTextView = new TextView(getContext());
        subCategoryDescTextView.setText(mSubCategory.getSubCategoryDesc());
        subCategoryDescTextView.setTextColor(mColor);
        subCategoryDescTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        // 设置TextView布局参数
        RelativeLayout.LayoutParams subCategoryDescLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        subCategoryDescLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        subCategoryDescTextView.setLayoutParams(subCategoryDescLayoutParams);
        // 添加到布局
        mBannerContainerRelativeLayout.addView(subCategoryDescTextView);
    }

    public void initRecyclerView(final List<Goods> goodsList) {
        // 1. 初始化VirtualLayoutManager
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(getContext());
        // 2. 将VirtualLayoutManager绑定到RecyclerView
        mGoodsRecyclerView.setLayoutManager(layoutManager);
        // 3. 设置回收复用池大小
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mGoodsRecyclerView.setRecycledViewPool(viewPool);
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
        mGoodsRecyclerView.setAdapter(delegateAdapter);
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

    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    private int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

}
