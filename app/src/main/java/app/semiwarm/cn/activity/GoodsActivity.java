package app.semiwarm.cn.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.greendao.query.Query;

import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.application.MainApplication;
import app.semiwarm.cn.entity.CartGoods;
import app.semiwarm.cn.entity.CartGoodsDao;
import app.semiwarm.cn.entity.Goods;
import app.semiwarm.cn.fragment.GoodsDetailFragment;
import app.semiwarm.cn.fragment.GoodsInfoFragment;
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.service.observable.GoodsServiceObservable;
import app.semiwarm.cn.view.GoodsView;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class GoodsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.goodsView)
    GoodsView mGoodsView;
    @BindView(R.id.fab)
    FloatingActionButton mBtnToTop;
    @BindView(R.id.tv_add_cart)
    TextView mAddToCartTextView;
    @BindView(R.id.iv_cart)
    ImageView mCartImageView;

    private GoodsInfoFragment mGoodsInfoFragment;
    private GoodsDetailFragment mGoodsDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        // 设置深色状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        mBtnToTop.setOnClickListener(this);
        mAddToCartTextView.setOnClickListener(this);
        mCartImageView.setOnClickListener(this);
        // 首先需要获取传过来的商品id
        Long goodsId = getIntent().getLongExtra("goodsId", -1);
        // 请求商品数据
        if (goodsId != -1) {
            GoodsServiceObservable goodsService = new GoodsServiceObservable();
            goodsService.getGoodsById(goodsId).subscribe(new Subscriber<BaseResponse<Goods>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(BaseResponse<Goods> goodsBaseResponse) {
                    if (goodsBaseResponse.getSuccess() == 1) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Goods", goodsBaseResponse.getData());
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        mGoodsInfoFragment = new GoodsInfoFragment();
                        mGoodsInfoFragment.setArguments(bundle);
                        transaction.replace(R.id.fl_goods_info, mGoodsInfoFragment);
                        mGoodsDetailFragment = new GoodsDetailFragment();
                        mGoodsDetailFragment.setArguments(bundle);
                        transaction.replace(R.id.fl_goods_detail, mGoodsDetailFragment);
                        transaction.commit();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                mGoodsDetailFragment.goTop();
                mGoodsView.goTop(new GoodsView.OnTopListener() {
                    @Override
                    public void onTop() {
                        mGoodsInfoFragment.goTop();
                    }
                });
                break;
            case R.id.tv_add_cart:
                // 发出获取商品参数请求
                EventBus.getDefault().post(1000);
                break;
            case R.id.iv_cart:
                startActivity(new Intent(this, CartActivity.class));
                break;
        }
    }

    @Subscribe
    public void onEvent(CartGoods cartGoods) {
        // 接收商品数据
        if (null != cartGoods) {
            Log.i("CartGoods:", cartGoods.toString());
            // 接收到数据之后就可以添加到本地数据库了
            CartGoodsDao cartGoodsDao = MainApplication.getInstance().getDaoSession().getCartGoodsDao();
            // 在加购之前需要判断是否是同一种商品，如果是只需要更新数据库中该商品的数量即可
            Query<CartGoods> query = cartGoodsDao
                    .queryBuilder()
                    .where(CartGoodsDao.Properties.GoodsId.eq(cartGoods.getGoodsId()),
                            CartGoodsDao.Properties.GoodsSpecParam.eq(cartGoods.getGoodsSpecParam()))
                    .build();
            List<CartGoods> cartGoodsList = query.list();
            Log.i("cartGoodsList:", cartGoodsList.toString());
            if (cartGoodsList.size() > 0) {
                cartGoodsList.get(0).setGoodsCount(cartGoodsList.get(0).getGoodsCount() + cartGoods.getGoodsCount());
                cartGoodsDao.update(cartGoodsList.get(0));
                Toast.makeText(this, "购物车商品数量修改成功!", Toast.LENGTH_SHORT).show();
            } else {
                long result = cartGoodsDao.insert(cartGoods);
                if (result > 0) {
                    Toast.makeText(this, "加购成功!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
