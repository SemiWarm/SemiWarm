package app.semiwarm.cn.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * ImagePickerAdapter
 * Created by alibct on 2017/5/14.
 */

public class ImagePickerAdapter {

    private ImageView mImageView;
    private int position;

    public class SelectedPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(int position) {
            //设置条目的点击事件
            itemView.setOnClickListener(this);
            //根据条目位置设置图片
//            ImageItem item = mData.get(position);
//            if (isAdded && position == getItemCount() - 1) {
//                iv_img.setImageResource(R.drawable.selector_image_add);
//                clickPosition = WxDemoActivity.IMAGE_ITEM_ADD;
//            } else {
//                ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, item.path, iv_img, 0, 0);
//                clickPosition = position;
//            }
        }

        @Override
        public void onClick(View v) {

        }
    }
}
