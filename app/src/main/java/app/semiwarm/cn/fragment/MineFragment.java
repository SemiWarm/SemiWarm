package app.semiwarm.cn.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.semiwarm.cn.R;
import app.semiwarm.cn.activity.UserCenterActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.ci_user_avatar)
    CircleImageView mUserAvatarCircleImageView;
    @BindView(R.id.tv_user_level)
    TextView mUserLevelTextView;
    @BindView(R.id.tv_user_name)
    TextView mUserNameTextView;
    @BindView(R.id.tv_my_order)
    TextView mMyOrderTextView;
    @BindView(R.id.tv_my_cart)
    TextView mMyCartTextView;
    @BindView(R.id.tv_waiting_pay)
    TextView mWaitingPayTextView;
    @BindView(R.id.tv_waiting_delivery)
    TextView mWaitingDeliveryTextView;
    @BindView(R.id.tv_deliveried)
    TextView mDeliveriedTextView;
    @BindView(R.id.tv_comment)
    TextView mCommentTextView;
    @BindView(R.id.tv_my_center)
    TextView mMyCenterTextView;
    @BindView(R.id.tv_my_address)
    TextView mMyAddressTextView;
    @BindView(R.id.tv_my_collection)
    TextView mMyCollectionTextView;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        mUserAvatarCircleImageView.setOnClickListener(this);
        mMyCenterTextView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ci_user_avatar:
                startActivity(new Intent(getContext(), UserCenterActivity.class));
                break;
            case R.id.tv_my_order:
                break;
            case R.id.tv_my_cart:
                break;
            case R.id.tv_waiting_pay:
                break;
            case R.id.tv_waiting_delivery:
                break;
            case R.id.tv_deliveried:
                break;
            case R.id.tv_comment:
                break;
            case R.id.tv_my_center:
                startActivity(new Intent(getContext(), UserCenterActivity.class));
                break;
            case R.id.tv_my_address:
                break;
            case R.id.tv_my_collection:
                break;
        }
    }
}
