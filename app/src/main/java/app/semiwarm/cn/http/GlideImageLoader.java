package app.semiwarm.cn.http;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;

import app.semiwarm.cn.R;

/**
 * GlideImageLoader
 * Created by alibct on 2017/5/14.
 */

public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide
                .with(activity)
                .load(path)
                .placeholder(R.drawable.ic_default_picture)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
