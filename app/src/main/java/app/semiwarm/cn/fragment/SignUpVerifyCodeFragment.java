package app.semiwarm.cn.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import app.semiwarm.cn.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 验证验证码
 * A simple {@link Fragment} subclass.
 */
public class SignUpVerifyCodeFragment extends Fragment {

    @BindView(R.id.et_code)
    EditText mCodeEditText;

    private String code;
    private String phone;


    public SignUpVerifyCodeFragment() {
        EventBus.getDefault().register(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_verify_code, container, false);
        ButterKnife.bind(this, view);

        mCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (code.equals(s.toString())) {
                        // 验证码相等的时候先实例化Fragment
                        Fragment signUpFragment = new SignUpFragment();
                        // 然后发送数据
                        EventBus.getDefault().post(phone);
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fl_sign_up, signUpFragment)
                                .commit();
                    }
                }
            }
        });

        return view;
    }

    @Subscribe
    public void onEvent(HashMap<String, String> hashMap) {
        code = hashMap.get("code");
        phone = hashMap.get("phone");
        Log.i("接收的code:", code);
        Log.i("接收的phone:", phone);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
