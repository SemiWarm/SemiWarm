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
import app.semiwarm.cn.entity.SubCategory;
import app.semiwarm.cn.fragment.SubCategoryItemClickListener;

/**
 * SubCategoryAdapter
 * Created by alibct on 2017/5/6.
 */

public class SubCategoryAdapter extends DelegateAdapter.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {

    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private VirtualLayoutManager.LayoutParams mLayoutParams;
    // 子类目数据
    private List<SubCategory> mSubCategories;
    // item点击事件
    private SubCategoryItemClickListener mItemClickListener;

    public SubCategoryAdapter(Context context, LayoutHelper layoutHelper, VirtualLayoutManager.LayoutParams layoutParams, List<SubCategory> subCategories) {
        mContext = context;
        mLayoutHelper = layoutHelper;
        mLayoutParams = layoutParams;
        mSubCategories = subCategories;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public SubCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SubCategoryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_subcategory_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SubCategoryViewHolder holder, int position) {
        holder.getTextView().setText(mSubCategories.get(position).getSubCategoryName());
        Glide
                .with(mContext)
                .load(mSubCategories.get(position).getSubCategoryIcon())
                .into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return null == mSubCategories ? 0 : mSubCategories.size();
    }

    public void setItemClickListener(SubCategoryItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }


    class SubCategoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTextView;

        SubCategoryViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_subcategory_icon);
            mTextView = (TextView) itemView.findViewById(R.id.tv_subcategory_name);
            // 点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }

        ImageView getImageView() {
            return mImageView;
        }

        TextView getTextView() {
            return mTextView;
        }
    }
}
