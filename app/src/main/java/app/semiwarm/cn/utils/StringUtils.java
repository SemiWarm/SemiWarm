package app.semiwarm.cn.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义的字符串处理类
 * Created by alibct on 2017/3/5.
 */

public class StringUtils {
    public static boolean isPhoneNumber(String phoneNumber) {
        // 使用正则表达式判断是否为手机号
        Pattern pattern = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static String getRandomCode() {
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 4; i++) {
            int temp = random.nextInt(10);
            code += temp;
        }
        return code;
    }
}
