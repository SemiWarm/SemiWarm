package app.semiwarm.cn.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.service.observable.UserServiceObservable;
import app.semiwarm.cn.utils.EditTextUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

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
    private ProgressDialog dialog;

    public SignUpFragment() {
        // 使用EventBus进行注册需要接受消息的Fragment
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        // 一定要绑定ButterKnife否则会出现空指针异常
        ButterKnife.bind(this, view);
        // 用户名输入框获取焦点
        mAccountNameEditText.setFocusable(true);
        // 添加一键清除监听
        EditTextUtils.addShowPasswordListener(mPasswordEditText, mPasswordEyeImageView);
        EditTextUtils.addShowPasswordListener(mConfirmPasswordEditText, mConfirmPasswordEyeImageView);
        // 添加用户名输入框监听，防止用户没有输入用户名
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
                    Toast.makeText(getActivity(), "小主您忘了给自己起名字啦!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 一键清除按钮监听
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
                // 当输入框失去焦点时说明用户正在输入其它信息，此时应该检查名称合法性
                if (!hasFocus) {
                    if (mAccountNameEditText.getText().length() > 0) {
                        isAccountNameAvailable();
                    } else {
                        mAccountNameAvailableImageView.setImageResource(R.drawable.ic_error);
                        mAccountNameAvailableImageView.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "小主您忘了给自己起名字啦!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), "密码不能少于6位啦!常识哦!", Toast.LENGTH_SHORT).show();
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
                // 设置注册按钮不可用
                mSignUpButton.setEnabled(false);
                // 开始加载对话框
                dialog = new ProgressDialog(getContext());
                dialog.setCancelable(false); // 不可以被取消
                dialog.setTitle("注册中...");
                dialog.setMessage("正在为小主玩命抢注ing...");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
                // 在此检测用户是否输入用户名，防止一些很皮的用户暴力测试
                if (mAccountNameEditText.getText().length() > 0) {
                    // 初始化请求服务
                    final UserServiceObservable userService = new UserServiceObservable();
                    userService.getUserByName(mAccountNameEditText.getText().toString())
                            .subscribe(new Subscriber<BaseResponse<User>>() {
                                @Override
                                public void onCompleted() {
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.i("onError:", e.getCause().toString());
                                }

                                @Override
                                public void onNext(BaseResponse<User> userBaseResponse) {
                                    User user = userBaseResponse.getData();
                                    Log.i("success:", "" + userBaseResponse.getSuccess());
                                    Log.i("message:", userBaseResponse.getMessage());
                                    if (null != user) {
                                        Log.i("user:", user.toString());
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), "小主想到的名字已经被别人抢先啦!", Toast.LENGTH_SHORT).show();
                                        mAccountNameAvailableImageView.setImageResource(R.drawable.ic_error);
                                        mAccountNameAvailableImageView.setVisibility(View.VISIBLE);
                                        mSignUpButton.setEnabled(true);
                                    } else {
                                        // 初始化用户
                                        User registerUser = new User();
                                        // 设置用户手机号
                                        registerUser.setUserAccount(phone);
                                        // 设置用户名
                                        registerUser.setUserName(mAccountNameEditText.getText().toString());
                                        // 再进行一次判断
                                        if (mPasswordEditText.getText().length() >= 6 && mConfirmPasswordEditText.getText().length() >= 6) {
                                            if (mConfirmPasswordEditText.getText().toString().equals(mPasswordEditText.getText().toString())) {
                                                // 设置用户密码
                                                registerUser.setLoginPassword(mConfirmPasswordEditText.getText().toString());
                                                // 调用注册接口
                                                userService.signUp(registerUser)
                                                        .subscribe(new Subscriber<BaseResponse<User>>() {
                                                            @Override
                                                            public void onCompleted() {

                                                            }

                                                            @Override
                                                            public void onError(Throwable e) {
                                                                Log.i("onError", e.getCause().toString());
                                                            }

                                                            @Override
                                                            public void onNext(BaseResponse<User> userBaseResponse) {
                                                                if (userBaseResponse.getSuccess() == 1) {
                                                                    dialog.setTitle(userBaseResponse.getMessage());
                                                                    dialog.setMessage("正在玩命加载登录界面ing...");
                                                                    new CountDownTimer(2000, 1000) {

                                                                        @Override
                                                                        public void onTick(long millisUntilFinished) {
                                                                        }

                                                                        @Override
                                                                        public void onFinish() {
                                                                            dialog.dismiss();
                                                                            startActivity(new Intent(getActivity(), SignInActivity.class));
                                                                            getActivity().finish();
                                                                        }
                                                                    }.start();
                                                                } else {
                                                                    dialog.setTitle(userBaseResponse.getMessage());
                                                                    dialog.setMessage(userBaseResponse.getMessage());
                                                                    dialog.dismiss();
                                                                    mSignUpButton.setEnabled(true);
                                                                }
                                                            }
                                                        });
                                            } else {
                                                dialog.dismiss();
                                                mSignUpButton.setEnabled(true);
                                                Toast.makeText(getActivity(), "两次密码怎么不一致呢?", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            dialog.dismiss();
                                            mSignUpButton.setEnabled(true);
                                            Toast.makeText(getActivity(), "密码不能少于6位啦!常识哦!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                } else

                {
                    dialog.dismiss();
                    mSignUpButton.setEnabled(true);
                    Toast.makeText(getActivity(), "小主您忘了给自己起名字啦!", Toast.LENGTH_SHORT).show();
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
        UserServiceObservable userService = new UserServiceObservable();
        userService.getUserByName(mAccountNameEditText.getText().toString())
                .subscribe(new Subscriber<BaseResponse<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("onError:", e.getCause().toString());
                    }

                    @Override
                    public void onNext(BaseResponse<User> userBaseResponse) {
                        User user = userBaseResponse.getData();
                        Log.i("success:", "" + userBaseResponse.getSuccess());
                        Log.i("message:", userBaseResponse.getMessage());
                        if (null != user) {
                            Log.i("user:", user.toString());
                            Toast.makeText(getActivity(), "小主想到的名字已经被别人抢先啦!", Toast.LENGTH_SHORT).show();
                            mAccountNameAvailableImageView.setImageResource(R.drawable.ic_error);
                            mAccountNameAvailableImageView.setVisibility(View.VISIBLE);
                        } else {
                            mAccountNameAvailableImageView.setImageResource(R.drawable.ic_correct);
                            mAccountNameAvailableImageView.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
