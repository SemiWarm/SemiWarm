package app.semiwarm.cn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bumptech.glide.Glide;

import java.util.List;

import app.semiwarm.cn.R;
import app.semiwarm.cn.entity.Goods;
import app.semiwarm.cn.fragment.SearchGoodsItemClickListener;

/**
 * SearchGoodsAdapter
 * Created by alibct on 2017/5/17.
 */

public class SearchGoodsAdapter extends DelegateAdapter.Adapter<SearchGoodsAdapter.SearchGoodsViewHolder> {

    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private VirtualLayoutManager.LayoutParams mLayoutParams;
    // 搜索得到的商品数据
    private List<Goods> mGoodsList;
    // item点击事件
    private SearchGoodsItemClickListener mSearchGoodsItemClickListener;

    public void setSearchGoodsItemClickListener(SearchGoodsItemClickListener searchGoodsItemClickListener) {
        mSearchGoodsItemClickListener = searchGoodsItemClickListener;
    }

    public SearchGoodsAdapter(Context context, LayoutHelper layoutHelper, VirtualLayoutManager.LayoutParams layoutParams, List<Goods> goodsList) {
        mContext = context;
        mLayoutHelper = layoutHelper;
        mLayoutParams = layoutParams;
        mGoodsList = goodsList;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public SearchGoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchGoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_search_goods_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchGoodsViewHolder holder, int position) {
        String goodsBanner = mGoodsList.get(position).getGoodsBanners().split(";")[0];
        Glide
                .with(mContext)
                .load(goodsBanner)
                .into(holder.getGoodsBanner());
        holder.getGoodsDesc().setText(mGoodsList.get(position).getGoodsDesc());
        holder.getGoodsTags().setText(mGoodsList.get(position).getGoodsTags());
        holder.getGoodsTitle().setText(mGoodsList.get(position).getGoodsTitle());
        holder.getGoodsPrice().setText("¥ " + mGoodsList.get(position).getGoodsPrice());
    }

    @Override
    public int getItemCount() {
        return null == mGoodsList ? 0 : mGoodsList.size();
    }


    class SearchGoodsViewHolder extends RecyclerView.ViewHolder {

        private ImageView mGoodsBanner;
        private TextView mGoodsDesc;
        private TextView mGoodsTags;
        private TextView mGoodsTitle;
        private TextView mGoodsPrice;

        public SearchGoodsViewHolder(View itemView) {
            super(itemView);
            mGoodsBanner = (ImageView) itemView.findViewById(R.id.iv_goods_banner);
            mGoodsDesc = (TextView) itemView.findViewById(R.id.tv_goods_desc);
            mGoodsTags = (TextView) itemView.findViewById(R.id.tv_goods_tags);
            mGoodsTitle = (TextView) itemView.findViewById(R.id.tv_goods_title);
            mGoodsPrice = (TextView) itemView.findViewById(R.id.tv_goods_price);
            // 设置点击事件回调
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSearchGoodsItemClickListener != null) {
                        mSearchGoodsItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }

        public ImageView getGoodsBanner() {
            return mGoodsBanner;
        }

        public TextView getGoodsDesc() {
            return mGoodsDesc;
        }

        public TextView getGoodsTags() {
            return mGoodsTags;
        }

        public TextView getGoodsTitle() {
            return mGoodsTitle;
        }

        public TextView getGoodsPrice() {
            return mGoodsPrice;
        }
    }
}
