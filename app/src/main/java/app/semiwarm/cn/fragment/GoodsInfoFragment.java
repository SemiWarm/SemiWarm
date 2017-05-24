package app.semiwarm.cn.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.ArrayList;
import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.adapter.ViewPagerAdapter;
import app.semiwarm.cn.entity.Goods;
import app.semiwarm.cn.view.VerticalScrollView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品信息Fragment
 * A simple {@link Fragment} subclass.
 */
public class GoodsInfoFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.bottomsheet)
    BottomSheetLayout mBottomSheetLayout;
    @BindView(R.id.vsv_goods_info)
    VerticalScrollView mGoodsInfoScrollView;

    @BindView(R.id.vp_goods_banners)
    ViewPager mGoodsBannersViewPager;
    @BindView(R.id.tv_goods_title)
    TextView mGoodsTitleTextView;
    @BindView(R.id.tv_goods_desc)
    TextView mGoodsDescTextView;
    @BindView(R.id.tv_goods_price)
    TextView mGoodsPriceTextView;
    @BindView(R.id.tv_goods_provider)
    TextView mGoodsProviderTextView;
    @BindView(R.id.ll_select_goods_spec)
    LinearLayout mSelectSpecLinearLayout;

    private Goods mGoods;

    private GoodsSpecParamFragment mGoodsSpecParamFragment;

    public GoodsInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_info, container, false);
        ButterKnife.bind(this, view);
        // 获取Goods
        mGoods = (Goods) getArguments().getSerializable("Goods");
        // 初始化商品主图
        List<ImageView> imageViews = new ArrayList<>();
        ImageView imageView;
        if (null != mGoods) {
            String[] banners = mGoods.getGoodsBanners().split(";");
            for (String banner : banners) {
                imageView = new ImageView(getContext());
                Glide.with(this).load(banner).centerCrop().into(imageView);
                imageViews.add(imageView);
            }
            // 设置adapter
            ViewPagerAdapter adapter = new ViewPagerAdapter(imageViews);
            mGoodsBannersViewPager.setAdapter(adapter);
            // 设置其它商品数据
            mGoodsTitleTextView.setText(mGoods.getGoodsTitle());
            mGoodsDescTextView.setText(mGoods.getGoodsDesc());
            mGoodsPriceTextView.setText("¥ " + mGoods.getGoodsPrice());
            mSelectSpecLinearLayout.setOnClickListener(this);
            // 干点其他事情
            mGoodsSpecParamFragment = new GoodsSpecParamFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("Goods", mGoods);
            mGoodsSpecParamFragment.setArguments(bundle);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_goods_spec:
                mGoodsSpecParamFragment.show(getFragmentManager(), R.id.bottomsheet);
                break;
        }
    }

    @Override
    public void goTop() {
        mGoodsInfoScrollView.goTop();
    }
}
