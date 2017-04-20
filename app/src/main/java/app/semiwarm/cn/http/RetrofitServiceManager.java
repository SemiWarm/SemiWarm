package app.semiwarm.cn.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求封装类
 * Created by alibct on 2017/4/19.
 */

public class RetrofitServiceManager {

    private static final int DEFAULT_TIME_OUT = 5; //超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 5; //写操作超时时间
    private static final int DEFAULT_WRITE_TIME_OUT = 5; //读操作超时时间
    private Retrofit mRetrofit;

    // 接口配置类
    private class ApiConfig {
        private static final String BASE_URL = "http://semiwarm.cn/api/v1.0/";
    }

    private RetrofitServiceManager() {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS); //连接超时时间
        builder.writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS); //写操作超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS); //读操作超时时间
        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConfig.BASE_URL)
                .build();
    }

    private static class SingletonHolder {
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    /**
     * 获取RetrofitServiceManager
     *
     * @return RetrofitServiceManager
     */
    public static RetrofitServiceManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>     范型
     * @return 范型
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
