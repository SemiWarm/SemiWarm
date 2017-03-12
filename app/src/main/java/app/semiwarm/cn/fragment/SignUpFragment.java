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
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import app.semiwarm.cn.R;
import app.semiwarm.cn.entity.User;
import app.semiwarm.cn.service.UserService;
import app.semiwarm.cn.utils.EditTextClearUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    @BindView(R.id.iv_account_name_available)
    ImageView mAccountNameAvailableImageView;
    @BindView(R.id.iv_account_name_clear)
    ImageView mAccountNameClearImageView;
    @BindView(R.id.et_password)
    EditText mPasswordEditText;
    @BindView(R.id.iv_password_available)
    ImageView mPasswordAvailableImageView;
    @BindView(R.id.iv_password_clear)
    ImageView mPasswordClearImageView;
    @BindView(R.id.et_confirm_password)
    EditText mConfirmPasswordEditText;
    @BindView(R.id.iv_confirm_password_available)
    ImageView mConfirmPasswordAvailableImageView;
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

        mAccountNameEditText.setFocusable(true);

        // 添加一键清除
        EditTextClearUtils.addClearListener(mAccountNameEditText, mAccountNameClearImageView);
        EditTextClearUtils.addClearListener(mPasswordEditText, mPasswordClearImageView);
        EditTextClearUtils.addClearListener(mConfirmPasswordEditText, mConfirmPasswordClearImageView);


        // 为用户名输入框添加失焦监听
        mAccountNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (mAccountNameEditText.getText().length() > 0) {
                        // 进行网络请求
                        // 初始化Retrofit
                        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())
                                .baseUrl("http://semiwarm.cn/api/v1.0/") // 这里的baseUrl中的参数必须以"/"结尾
                                .build();
                        // 初始化UserService
                        UserService userService = retrofit.create(UserService.class);
                        Call<User> user = userService.getUserByName(mAccountNameEditText.getText().toString());
                        user.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (null != response.body()) {
                                    Toast.makeText(getActivity(), "小主想到的名字已经被别人抢占啦！", Toast.LENGTH_SHORT).show();
                                    mAccountNameAvailableImageView.setVisibility(View.VISIBLE);
                                    mAccountNameAvailableImageView.setImageResource(R.drawable.ic_error);
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                mAccountNameAvailableImageView.setVisibility(View.VISIBLE);
                                mAccountNameAvailableImageView.setImageResource(R.drawable.ic_correct);
                            }
                        });
                    } else {
                        mAccountNameAvailableImageView.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "小主你忘了给自己请名字啦！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mAccountNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAccountNameAvailableImageView.setVisibility(View.INVISIBLE);
            }
        });

        mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (mPasswordEditText.getText().length() < 6){
                        mPasswordAvailableImageView.setVisibility(View.VISIBLE);
                        mPasswordAvailableImageView.setImageResource(R.drawable.ic_error);
                        Toast.makeText(getActivity(),"密码不能少于6位啦！常识哦！",Toast.LENGTH_SHORT).show();
                    } else {
                        mPasswordAvailableImageView.setVisibility(View.VISIBLE);
                        mPasswordAvailableImageView.setImageResource(R.drawable.ic_correct);
                    }
                }
            }
        });

        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPasswordAvailableImageView.setVisibility(View.INVISIBLE);
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
