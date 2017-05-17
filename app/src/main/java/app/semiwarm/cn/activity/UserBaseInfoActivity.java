package app.semiwarm.cn.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

import app.semiwarm.cn.R;
import app.semiwarm.cn.http.GlideImageLoader;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserBaseInfoActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.iv_back_button)
    ImageView mBtnBackImageView;
    @BindView(R.id.tv_update_user_info)
    TextView mUpdateInfoTextView;
    @BindView(R.id.ci_user_avatar)
    CircleImageView mUserAvatarCircleImageView;
    @BindView(R.id.bottomsheet)
    BottomSheetLayout mBottomSheetLayout;

    private Intent mIntent;
    private ArrayList<ImageItem> mImageItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_base_info);
        ButterKnife.bind(this);
        // 设置深色状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader()); //设置图片加载器
        imagePicker.setShowCamera(false);  //不显示拍照按钮
        imagePicker.setCrop(true); //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setMultiMode(false); // 开启单张图片模式
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800); //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800); //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000); //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000); //保存文件的高度。单位像素

        mBtnBackImageView.setOnClickListener(this);
        mUpdateInfoTextView.setOnClickListener(this);
        mUserAvatarCircleImageView.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_button:
                finish();
                break;
            case R.id.tv_update_user_info:
                Toast.makeText(UserBaseInfoActivity.this, "信息已更新!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ci_user_avatar:
                View view = LayoutInflater.from(this).inflate(R.layout.view_option_dialog, mBottomSheetLayout, false);
                mBottomSheetLayout.showWithSheetView(view);
                view.findViewById(R.id.tv_photo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mIntent = new Intent(UserBaseInfoActivity.this, ImageGridActivity.class);
                        startActivityForResult(mIntent, 100);
                        mBottomSheetLayout.dismissSheet();
                    }
                });
                view.findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetLayout.dismissSheet();
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                mImageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (null != mImageItems) {
                    Glide
                            .with(this)
                            .load(mImageItems.get(0).path)
                            .into(mUserAvatarCircleImageView);
                } else {
                    Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
