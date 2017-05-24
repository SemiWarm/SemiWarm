package app.semiwarm.cn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import app.semiwarm.cn.R;
import app.semiwarm.cn.entity.User;
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.service.observable.UserServiceObservable;
import app.semiwarm.cn.utils.EditTextUtils;
import app.semiwarm.cn.utils.SharedPreferencesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class SignInActivity extends BaseActivity {

    // 准备控件
    @BindView(R.id.tb_title)
    Toolbar mToolbar;
    @BindView(R.id.et_account)
    EditText mAccountEditText;
    @BindView(R.id.iv_account_clear)
    ImageView mAccountClearImageView;
    @BindView(R.id.et_password)
    EditText mPasswordEditText;
    @BindView(R.id.iv_password_eye)
    ImageView mPasswordEyeImageView;

    @BindView(R.id.tv_sign_up)
    TextView mGotoSignUpTextView;

    @BindView(R.id.tv_forget_password)
    TextView mForgetPasswordTextView;
    @BindView(R.id.btn_sign_in)
    Button mBtnSignIn;
//    @BindView(R.id.iv_third_qq)
//    ImageView mQQImageView;
//    @BindView(R.id.iv_third_wechat)
//    ImageView mWeChatImageView;
//    @BindView(R.id.iv_third_weibo)
//    ImageView mWeiBoImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // 绑定控件，否则会出现空指针异常
        ButterKnife.bind(this);

        // 设置Title
        mToolbar.setNavigationIcon(R.drawable.ic_btn_close);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EditTextUtils.addClearListener(mAccountEditText, mAccountClearImageView);
        EditTextUtils.addShowPasswordListener(mPasswordEditText, mPasswordEyeImageView);

        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mPasswordEyeImageView.setVisibility(View.VISIBLE);
                } else {
                    mPasswordEyeImageView.setVisibility(View.INVISIBLE);
                }
            }
        });

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAccountEditText.getText().toString().trim().length() > 0) {
                    // 开始请求
                    UserServiceObservable userService = new UserServiceObservable();
                    userService.getUserByAccount(mAccountEditText.getText().toString().trim()).subscribe(new Subscriber<BaseResponse<User>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BaseResponse<User> userBaseResponse) {
                            if (userBaseResponse.getSuccess() == 1) {
                                SharedPreferencesUtils.setUserAccount(getApplicationContext(), userBaseResponse.getData().getUserAccount());
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(SignInActivity.this, "不存在该用户!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignInActivity.this, "请输入手机号!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mGotoSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                finish();
            }
        });


    }
}
