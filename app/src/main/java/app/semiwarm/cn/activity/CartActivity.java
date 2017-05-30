package app.semiwarm.cn.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.ArrayList;
import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.adapter.CartGoodsAdapter;
import app.semiwarm.cn.application.MainApplication;
import app.semiwarm.cn.entity.CartGoods;
import app.semiwarm.cn.entity.CartGoodsDao;
import app.semiwarm.cn.fragment.CartGoodsItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.iv_back_button)
    ImageView mBackImageView;
    @BindView(R.id.tv_edit)
    TextView mEditTextView;
    @BindView(R.id.rv_cart_goods)
    RecyclerView mCartGoodsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        // 设置深色状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEditTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditTextView.getText().toString().equals("编辑")) {
                    mEditTextView.setText("完成");
                } else {
                    mEditTextView.setText("编辑");
                }
            }
        });
        // 获取购物车数据
        CartGoodsDao cartGoodsDao = MainApplication.getInstance().getDaoSession().getCartGoodsDao();
        List<CartGoods> cartGoodsList = cartGoodsDao.loadAll();
        Log.i("cartGoodsList:", cartGoodsList.toString());
        // 初始化RecyclerView
        initRecyclerView(cartGoodsList);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void initRecyclerView(final List<CartGoods> cartGoodsList) {
        // 1. 初始化VirtualLayoutManager
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        // 2. 将VirtualLayoutManager绑定到RecyclerView
        mCartGoodsRecyclerView.setLayoutManager(layoutManager);
        // 3. 设置回收复用池大小
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mCartGoodsRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        // 4. 设置LinearLayoutHelper
        LinearLayoutHelper layoutHelper = new LinearLayoutHelper(); // 设置3列
        // 5. 设置LinearLayoutHelper其它属性
        layoutHelper.setItemCount(cartGoodsList.size());
        // 6. 初始化Adapter
        CartGoodsAdapter cartGoodsAdapter = new CartGoodsAdapter(this, layoutHelper, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), cartGoodsList);
        // 7. 设置Adapter
        List<DelegateAdapter.Adapter> adapters = new ArrayList<>();
        adapters.add(cartGoodsAdapter);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);
        mCartGoodsRecyclerView.setAdapter(delegateAdapter);
        // 8. 设置点击事件
        cartGoodsAdapter.setCartGoodsItemClickListener(new CartGoodsItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.rb_is_selected);
                // 取反
                cartGoodsList.get(postion).setIsChecked(!cartGoodsList.get(postion).getChecked());
                // 设值
                checkBox.setChecked(cartGoodsList.get(postion).getChecked());
            }
        });
    }
}
