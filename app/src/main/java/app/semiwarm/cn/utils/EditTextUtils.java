package app.semiwarm.cn.utils;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 输入框工具
 * Created by alibct on 2017/3/2.
 */

public class EditTextUtils {
    // 添加一键清除监听
    public static void addClearListener(final EditText editText, final ImageView imageView) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.INVISIBLE);
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
    }

    // 添加查看密码监听
    public static void addShowPasswordListener(final EditText editText, final ImageView imageView) {
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int touchEvent = event.getAction();
                switch (touchEvent){
                    case MotionEvent.ACTION_DOWN:
                        setPasswordVisibility(editText,true);
                        // 将光标置尾
                        editText.setSelection(editText.getText().length());
                        break;
                    case MotionEvent.ACTION_UP:
                        setPasswordVisibility(editText,false);
                        editText.setSelection(editText.getText().length());
                        break;
                }
                return true;
            }
        });
    }

    private static void setPasswordVisibility(EditText editText, boolean flag) {
        int inputType = flag ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText.setInputType(inputType);
    }
}
