package app.semiwarm.cn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import app.semiwarm.cn.R;
import app.semiwarm.cn.fragment.SignUpVerifyPhoneFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends BaseActivity {

    @BindView(R.id.tb_title)
    Toolbar mToolbar;
    @BindView(R.id.tv_sign_in)
    TextView mGotoSignInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // 绑定控件，否则会出现空指针异常
        ButterKnife.bind(this);

        mToolbar.setNavigationIcon(getDrawable(R.drawable.ic_btn_close));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mGotoSignInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });

        Fragment signUpVerifyFragment = new SignUpVerifyPhoneFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fl_sign_up, signUpVerifyFragment).commit();

    }
}
