package app.semiwarm.cn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bumptech.glide.Glide;

import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.entity.CartGoods;
import app.semiwarm.cn.fragment.CartGoodsItemClickListener;

/**
 * CartGoodsAdapter
 * Created by alibct on 2017/5/17.
 */

public class CartGoodsAdapter extends DelegateAdapter.Adapter<CartGoodsAdapter.CartGoodsViewHolder> {

    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private VirtualLayoutManager.LayoutParams mLayoutParams;
    // 搜索得到的商品数据
    private List<CartGoods> mCartGoodsList;
    // item点击事件
    private CartGoodsItemClickListener mCartGoodsItemClickListener;

    public CartGoodsAdapter(Context context, LayoutHelper layoutHelper, VirtualLayoutManager.LayoutParams layoutParams, List<CartGoods> cartGoodsList) {
        mContext = context;
        mLayoutHelper = layoutHelper;
        mLayoutParams = layoutParams;
        mCartGoodsList = cartGoodsList;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public CartGoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CartGoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_cart_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(CartGoodsViewHolder holder, int position) {
        holder.getCheckBox().setChecked(true);
        Glide
                .with(mContext)
                .load(mCartGoodsList.get(position).getGoodsBanner())
                .into(holder.getGoodsBanner());
        holder.getGoodsTitle().setText(mCartGoodsList.get(position).getGoodsTitle());
        holder.getGoodsSpecParam().setText(mCartGoodsList.get(position).getGoodsSpecParam());
        holder.getGoodsPrice().setText(mCartGoodsList.get(position).getGoodsPrice());
        holder.getGoodsCount().setText("x" + mCartGoodsList.get(position).getGoodsCount());
    }

    @Override
    public int getItemCount() {
        return null == mCartGoodsList ? 0 : mCartGoodsList.size();
    }

    public void setCartGoodsItemClickListener(CartGoodsItemClickListener cartGoodsItemClickListener) {
        mCartGoodsItemClickListener = cartGoodsItemClickListener;
    }

    class CartGoodsViewHolder extends RecyclerView.ViewHolder {

        private CheckBox mCheckBox;
        private ImageView mGoodsBanner;
        private TextView mGoodsTitle;
        private TextView mGoodsSpecParam;
        private TextView mGoodsPrice;
        private TextView mGoodsCount;

        public CheckBox getCheckBox() {
            return mCheckBox;
        }

        public ImageView getGoodsBanner() {
            return mGoodsBanner;
        }

        public TextView getGoodsTitle() {
            return mGoodsTitle;
        }

        public TextView getGoodsSpecParam() {
            return mGoodsSpecParam;
        }

        public TextView getGoodsPrice() {
            return mGoodsPrice;
        }

        public TextView getGoodsCount() {
            return mGoodsCount;
        }

        public CartGoodsViewHolder(View itemView) {
            super(itemView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.rb_is_selected);
            mGoodsBanner = (ImageView) itemView.findViewById(R.id.iv_goods_banner);
            mGoodsTitle = (TextView) itemView.findViewById(R.id.tv_goods_title);
            mGoodsSpecParam = (TextView) itemView.findViewById(R.id.tv_goods_spec);
            mGoodsPrice = (TextView) itemView.findViewById(R.id.tv_goods_price);
            mGoodsCount = (TextView) itemView.findViewById(R.id.tv_goods_count);
            // 设置点击事件回调
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCartGoodsItemClickListener != null) {
                        mCartGoodsItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }
}
