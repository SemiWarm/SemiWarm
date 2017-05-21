package app.semiwarm.cn.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;

import java.util.ArrayList;
import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.adapter.SearchGoodsAdapter;
import app.semiwarm.cn.entity.Goods;
import app.semiwarm.cn.fragment.SearchGoodsItemClickListener;
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.service.observable.GoodsServiceObservable;
import app.semiwarm.cn.utils.EditTextUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.iv_close_button)
    ImageView mCloseImageView;
    @BindView(R.id.et_search)
    EditText mSearchEditText;
    @BindView(R.id.iv_search_clear)
    ImageView mSearchClearImageView;
    @BindView(R.id.iv_search_button)
    ImageView mSearchImageView;
    @BindView(R.id.rv_search_container)
    RecyclerView mSearchContainerRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 设置深色状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }

        ButterKnife.bind(this);

        mCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        EditTextUtils.addClearListener(mSearchEditText, mSearchClearImageView);

        // 搜索点击事件
        mSearchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取搜索框内容
                String searchText = mSearchEditText.getText().toString().trim();
                // 进行数据请求
                GoodsServiceObservable goodsService = new GoodsServiceObservable();
                goodsService.searchGoods(searchText).subscribe(new Subscriber<BaseResponse<List<Goods>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<Goods>> listBaseResponse) {
                        if (listBaseResponse.getSuccess() == 1) {
                            // 初始化RecyclerView
                            initRecyclerView(listBaseResponse.getData());
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void initRecyclerView(final List<Goods> goodsList) {
        // 1. 初始化VirtualLayoutManager
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        // 2. 将VirtualLayoutManager绑定到RecyclerView
        mSearchContainerRecyclerView.setLayoutManager(layoutManager);
        // 3. 设置回收复用池大小
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mSearchContainerRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        // 4. 设置GridLayoutHelper
        GridLayoutHelper layoutHelper = new GridLayoutHelper(2); // 设置3列
        layoutHelper.setAutoExpand(false); // 是否自动填充空白区
        layoutHelper.setSpanCount(2); // 设置网格个数
        // 5. 设置GridLayoutHelper其它属性
        layoutHelper.setItemCount(goodsList.size()); // 设置布局中item的个数
        // 6. 初始化Adapter
        SearchGoodsAdapter searchGoodsAdapter = new SearchGoodsAdapter(this, layoutHelper, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), goodsList);
        // 7. 设置Adapter
        List<DelegateAdapter.Adapter> adapters = new ArrayList<>();
        adapters.add(searchGoodsAdapter);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);
        mSearchContainerRecyclerView.setAdapter(delegateAdapter);
        // 8. 设置点击事件
        searchGoodsAdapter.setSearchGoodsItemClickListener(new SearchGoodsItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Toast.makeText(SearchActivity.this, "商品id" + goodsList.get(postion).getGoodsId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchActivity.this,GoodsActivity.class);
                intent.putExtra("goodsId",goodsList.get(postion).getGoodsId());
                startActivity(intent);
            }
        });
    }
}
