package app.semiwarm.cn.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import app.semiwarm.cn.R;
import app.semiwarm.cn.utils.EditTextClearUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 注册主界面
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    // 准备控件
    @BindView(R.id.et_account_name)
    EditText mAccountNameEditText;
    @BindView(R.id.iv_account_name_clear)
    ImageView mAccountNameClearImageView;
    @BindView(R.id.et_password)
    EditText mPasswordEditText;
    @BindView(R.id.iv_password_clear)
    ImageView mPasswordClearImageView;
    @BindView(R.id.et_confirm_password)
    EditText mConfirmPasswordEditText;
    @BindView(R.id.iv_confirm_password_clear)
    ImageView mConfirmPasswordClearImageView;
    @BindView(R.id.btn_sign_up)
    Button mSignUpButton;

    private String phone;

    public SignUpFragment() {
        // Required empty public constructor
        // 使用EventBus进行注册需要接受消息的Fragment
        EventBus.getDefault().register(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        // 一定要绑定ButterKnife否则会出现空指针异常
        ButterKnife.bind(this, view);

        // 添加一键清除
        EditTextClearUtils.addClearListener(mAccountNameEditText, mAccountNameClearImageView);
        EditTextClearUtils.addClearListener(mPasswordEditText, mPasswordClearImageView);
        EditTextClearUtils.addClearListener(mConfirmPasswordEditText, mConfirmPasswordClearImageView);

        // 为用户名输入框添加监听
        mAccountNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    // 进行网络请求
                    // 初始化Retrofit
                    Retrofit retrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("http://semiwarm.cn/api/v1.0/") // 这里的baseUrl中的参数必须以"/"结尾
                            .build();
                }
            }
        });

        return view;
    }

    @Subscribe
    public void onEvent(String phone) {
        this.phone = phone;
        Log.i("phone:", phone);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
