package app.semiwarm.cn.fragment;


import android.content.Intent;
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
import app.semiwarm.cn.activity.SignInActivity;
import app.semiwarm.cn.entity.User;
import app.semiwarm.cn.service.UserService;
import app.semiwarm.cn.utils.EditTextUtils;
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
    @BindView(R.id.iv_password_eye)
    ImageView mPasswordEyeImageView;

    @BindView(R.id.et_confirm_password)
    EditText mConfirmPasswordEditText;
    @BindView(R.id.iv_confirm_password_available)
    ImageView mConfirmPasswordAvailableImageView;
    @BindView(R.id.iv_confirm_password_eye)
    ImageView mConfirmPasswordEyeImageView;

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

        EditTextUtils.addShowPasswordListener(mPasswordEditText, mPasswordEyeImageView);
        EditTextUtils.addShowPasswordListener(mConfirmPasswordEditText, mConfirmPasswordEyeImageView);

        mAccountNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mAccountNameClearImageView.setVisibility(View.VISIBLE);
                } else {
                    mAccountNameClearImageView.setVisibility(View.INVISIBLE);
                    mAccountNameAvailableImageView.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "小主你忘了给自己起名字啦！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAccountNameClearImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAccountNameEditText.setText("");
            }
        });

        // 为用户名输入框添加失焦监听
        mAccountNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // 当输入框失去聚焦时说明用户正在输入其他用户框，此时应该检查名称合法性
                if (!hasFocus) {
                    if (mAccountNameEditText.getText().length() > 0) {
                        isAccountNameAvailable();
                    } else {
                        mAccountNameAvailableImageView.setImageResource(R.drawable.ic_error);
                        mAccountNameAvailableImageView.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "小主你忘了给自己起名字啦！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 只要输入框获得焦点就说明用户正在编辑输入框，此时应该隐藏提示图标
                    mAccountNameAvailableImageView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // 对密码框添加焦点监听事件
        mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // 失去焦点时进行合法性检查
                if (!hasFocus) {
                    if (mPasswordEditText.getText().length() < 6) {
                        mPasswordAvailableImageView.setImageResource(R.drawable.ic_error);
                        mPasswordAvailableImageView.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "密码不能少于6位啦！常识哦！", Toast.LENGTH_SHORT).show();
                    } else {
                        mPasswordAvailableImageView.setImageResource(R.drawable.ic_correct);
                        mPasswordAvailableImageView.setVisibility(View.VISIBLE);
                    }
                } else {
                    // 编辑状态下的输入框应该显示查看图标
                    mPasswordEyeImageView.setVisibility(View.VISIBLE);
                    // 但是需要隐藏提示图标
                    mPasswordAvailableImageView.setVisibility(View.INVISIBLE);
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
                if (s.length() < 0) {
                    mPasswordAvailableImageView.setImageResource(R.drawable.ic_error);
                    mPasswordAvailableImageView.setVisibility(View.VISIBLE);
                }
            }
        });

        // 对确认密码框进行实时监听
        mConfirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mConfirmPasswordEyeImageView.setVisibility(View.VISIBLE);
                if (s.length() > 0) {
                    // 说明此时用户正在输入
                    if (mConfirmPasswordEditText.getText().toString().equals(mPasswordEditText.getText().toString())) {
                        mConfirmPasswordAvailableImageView.setImageResource(R.drawable.ic_correct);
                        mConfirmPasswordAvailableImageView.setVisibility(View.VISIBLE);
                        mSignUpButton.setEnabled(true);
                    } else {
                        mConfirmPasswordAvailableImageView.setImageResource(R.drawable.ic_error);
                        mConfirmPasswordAvailableImageView.setVisibility(View.VISIBLE);
                    }
                } else {
                    mConfirmPasswordAvailableImageView.setImageResource(R.drawable.ic_error);
                    mConfirmPasswordAvailableImageView.setVisibility(View.VISIBLE);
                }
            }
        });

        // 按钮事件
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAccountNameEditText.getText().length() > 0) {
                    // 请求注册接口
                    // 进行网络请求
                    // 初始化Retrofit
                    Retrofit retrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("http://semiwarm.cn/api/v1.0/") // 这里的baseUrl中的参数必须以"/"结尾
                            .build();
                    // 初始化UserService
                    final UserService userService = retrofit.create(UserService.class);
                    final Call<User> user = userService.getUserByName(mAccountNameEditText.getText().toString());
                    user.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (null != response.body()) {
                                Toast.makeText(getActivity(), "小主想到的名字已经被别人抢占啦！", Toast.LENGTH_SHORT).show();
                                mAccountNameAvailableImageView.setImageResource(R.drawable.ic_error);
                                mAccountNameAvailableImageView.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            // 初始化用户
                            User registerUser = new User();
                            // 设置用户手机号
                            registerUser.setUserPhone(phone);
                            // 设置用户名
                            registerUser.setUserName(mAccountNameEditText.getText().toString());
                            // 在进行一次判断
                            if (mPasswordEditText.getText().length() >= 6 && mConfirmPasswordEditText.getText().length() >= 6) {
                                if (mConfirmPasswordEditText.getText().toString().equals(mPasswordEditText.getText().toString())) {
                                    // 设置用户密码
                                    registerUser.setPassword(mConfirmPasswordEditText.getText().toString());
                                    // 调用注册接口
                                    Call<String> signUpResult = userService.signUp(registerUser);
                                    signUpResult.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            // 成功后在后台打印响应结果
                                            Log.i("signUpResult:", response.body());
                                            // 启动登录界面
                                            startActivity(new Intent(getActivity(), SignInActivity.class));
                                            getActivity().finish();
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), "两个密码怎么不一样呢？", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "密码不能少于6位啦！常识哦！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "小主你忘了给自己起名字啦！", Toast.LENGTH_SHORT).show();
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

    public void isAccountNameAvailable() {
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
                    mAccountNameAvailableImageView.setImageResource(R.drawable.ic_error);
                    mAccountNameAvailableImageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mAccountNameAvailableImageView.setImageResource(R.drawable.ic_correct);
                mAccountNameAvailableImageView.setVisibility(View.VISIBLE);
            }
        });
    }
}
