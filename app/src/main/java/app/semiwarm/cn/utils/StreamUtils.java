package app.semiwarm.cn.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 流数据转换
 * Created by alibct on 2017/3/6.
 */

public class StreamUtils {
    // 输入流转String标准写法
    public static String streamToString(InputStream inputStream) {
        String result = "";
        // 创建一个字节数组写入流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                // 写入字节流
                byteArrayOutputStream.write(buffer, 0, length);
                byteArrayOutputStream.flush();
            }
            // 将字节流转换成String
            result = byteArrayOutputStream.toString();
            // 关闭字节流
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
