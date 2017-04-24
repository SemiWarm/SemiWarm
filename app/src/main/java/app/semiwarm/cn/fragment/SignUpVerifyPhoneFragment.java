package app.semiwarm.cn.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import app.semiwarm.cn.R;
import app.semiwarm.cn.entity.MessageResponse;
import app.semiwarm.cn.entity.User;
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.service.observable.UserServiceObservable;
import app.semiwarm.cn.utils.EditTextUtils;
import app.semiwarm.cn.utils.MessageUtils;
import app.semiwarm.cn.utils.StringUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * 登录验证
 * A simple {@link Fragment} subclass.
 */
public class SignUpVerifyPhoneFragment extends Fragment {

    // 准备控件
    @BindView(R.id.et_phone)
    EditText mPhoneEditText;
    @BindView(R.id.iv_phone_clear)
    ImageView mPhoneClearImageView;
    @BindView(R.id.btn_get_code)
    Button mGetCodeButton;

    // 手机号
    private String phone;
    // 验证码
    private String code;

    // 常量
    private final static int SUCCESS = 1;
    private final static int FAILURE = -1;

    private ProgressDialog dialog;

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            // 第三方短信接口apiKey
            String apiKey = "1c083f2ff7ad72025e59836c6586d6fd";
            // 获取四位数随机验证码
            code = StringUtils.getRandomCode();
            // 构建短信内容
            String SMSTemplate = "【半暖商城】感谢您注册半暖，您的验证码是";
            String SMSContent = SMSTemplate + code;
            // 获取手机号
            phone = mPhoneEditText.getText().toString();
            // 在发送网络请求之前必须进行进行网络检查
            // ********我是分割线，记得进行网络检查********
            // 发送信息
            String response = MessageUtils.sendSignUpMessage(apiKey, SMSContent, phone);
            // 将JSON数据转化成MessageResponse对象
            MessageResponse messageResponse = JSON.parseObject(response, MessageResponse.class);
            // 获取信息对象
            Message message = Message.obtain();
            // 构建存储信息对象的Bundle
            Bundle bundle = new Bundle();
            if (null != messageResponse) {
                // 解析请求结果
                if (0 == messageResponse.getCode()) {
                    message.what = SUCCESS;
                    // 使用Bundle存储请求结果
                    bundle.putSerializable("messageResponse", messageResponse);
                    // 为message设置携带信息
                    message.setData(bundle);
                    // 发送message
                    mHandler.sendMessage(message);
                } else {
                    message.what = FAILURE;
                    bundle.putString("error", "验证码发送失败，请稍后再试!");
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                }
            } else {
                message.what = FAILURE;
                bundle.putString("error", "验证码发送失败，请稍后再试!");
                message.setData(bundle);
                mHandler.sendMessage(message);
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            // 收到信息后关闭对话框
            dialog.dismiss();
            // 从上文中Handler发送的信息中获取请求结果并存入Bundle
            Bundle bundle = message.getData();
            switch (message.what) {
                case SUCCESS:
                    MessageResponse messageResponse = (MessageResponse) bundle.getSerializable("messageResponse");
                    if (null != messageResponse) {
                        Log.i("messageResponse", messageResponse.toString());
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("code", code);
                        hashMap.put("phone", phone);

                        Fragment signUpVerifyCodeFragment = new SignUpVerifyCodeFragment();
                        EventBus.getDefault().post(hashMap);
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fl_sign_up, signUpVerifyCodeFragment)
                                .commit();

                    }
                    break;
                case FAILURE:
                    String error = bundle.getString("error");
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    // 设置获取验证码按钮为可用状态
                    mGetCodeButton.setEnabled(true);
                    break;
            }
        }
    };

    public SignUpVerifyPhoneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_verify_phone, container, false);
        // 一定要绑定数据
        ButterKnife.bind(this, view);

        // 为mPhoneEditText添加一键清空监听
        EditTextUtils.addClearListener(mPhoneEditText, mPhoneClearImageView);

        // 为mPhoneEditText添加内容监听
        mPhoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.length() > 0) {
                    // 输入不为空时进行手机号验证
                    if (StringUtils.isPhoneNumber(s.toString())) {
                        // 用户输入是手机号
                        // 获取验证码的按钮设置为可点击
                        mGetCodeButton.setEnabled(true);
                    } else {
                        // 将获取验证码按钮设置为不可用
                        mGetCodeButton.setEnabled(false);
                    }
                } else {
                    // 将获取验证码按钮设置为不可用
                    mGetCodeButton.setEnabled(false);
                }
            }
        });

        mGetCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击按钮开始加载对话框
                dialog = new ProgressDialog(getContext());
                dialog.setCancelable(false); // 不可以被取消
                dialog.setTitle("发送验证码");
                dialog.setMessage("正在为小主发送验证码...");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                // 设置获取验证码按钮不可用
                mGetCodeButton.setEnabled(false);
                // 显示加载对话框
                dialog.show();
                // 初始化UserService
                UserServiceObservable userService = new UserServiceObservable();
                userService.getUserByAccount(mPhoneEditText.getText().toString())
                        .subscribe(new Subscriber<BaseResponse<User>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(BaseResponse<User> userBaseResponse) {
                                if (userBaseResponse.getSuccess() == 1) {
                                    // 关闭加载对话框
                                    dialog.dismiss();
                                    // 提示用户错误信息
                                    Toast.makeText(getActivity(), "小主您的手机号已经被注册啦!", Toast.LENGTH_SHORT).show();
                                    // 设置获取验证码按钮可用
                                    mGetCodeButton.setEnabled(true);
                                } else {
                                    // 显示加载对话框
                                    dialog.show();
                                    // 开启网络请求线程
                                    new Thread(mRunnable).start();
                                }
                            }
                        });
            }
        });

        return view;
    }
}
