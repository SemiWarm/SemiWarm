package app.semiwarm.cn.utils;

import android.util.Log;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import static android.content.ContentValues.TAG;
import static app.semiwarm.cn.utils.StreamUtils.streamToString;

/**
 * 短信服务类
 * Created by alibct on 2017/3/6.
 */

public class MessageUtils {

    /**
     * 发送注册验证码
     *
     * @param apikey 1c083f2ff7ad72025e59836c6586d6fd
     * @param text   【半暖商城】感谢您注册#app#，您的验证码是#code#
     * @param mobile 发送的手机号
     * @return json数据
     * <p>
     * post("https://sms.yunpian.com/v2/sms/single_send.json", params)
     */
    public static String sendSignUpMessage(String apikey, String text, String mobile) {
        HashMap<String, String> paramsMap = new HashMap<>(); // 请求参数集合
        paramsMap.put("apikey", apikey);
        paramsMap.put("text", text);
        paramsMap.put("mobile", mobile);
        return requestPost("https://sms.yunpian.com/v2/sms/single_send.json", paramsMap);
    }

    private static String requestPost(String baseUrl, HashMap<String, String> paramsMap) {
        try {
            // 合成参数
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String params = tempParams.toString();
            // 请求的参数转换为byte数组
            byte[] postData = params.getBytes();
            // 新建一个URL对象
            URL url = new URL(baseUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            urlConn.setConnectTimeout(5 * 1000);
            // 设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // Post请求必须设置允许输出 默认false
            urlConn.setDoOutput(true);
            // 设置请求允许输入 默认是true
            urlConn.setDoInput(true);
            // Post请求不能使用缓存
            urlConn.setUseCaches(false);
            // 设置为Post请求
            urlConn.setRequestMethod("POST");
            // 设置本次连接是否自动处理重定向
            urlConn.setInstanceFollowRedirects(true);
            // 配置请求Content-Type
            urlConn.setRequestProperty("Accept", "application/json;charset=utf-8;");
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8;");
            // 开始连接
            urlConn.connect();
            // 发送请求参数
            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
            dos.write(postData);
            dos.flush();
            dos.close();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                String result = streamToString(urlConn.getInputStream());
                Log.i(TAG, "Post方式请求成功，result--->" + result);
                return result;
            } else {
                Log.i(TAG, "Post方式请求失败");
            }
            // 关闭连接
            urlConn.disconnect();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }
}
