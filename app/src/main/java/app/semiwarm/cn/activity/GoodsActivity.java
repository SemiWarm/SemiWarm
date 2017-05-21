package app.semiwarm.cn.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import app.semiwarm.cn.R;
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

    private GoodsInfoFragment mGoodsInfoFragment;
    private GoodsDetailFragment mGoodsDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);
        // 设置深色状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        mBtnToTop.setOnClickListener(this);
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
        }
    }
}
