package app.semiwarm.cn.service;

import java.util.List;

import app.semiwarm.cn.entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 用户服务类接口
 * Created by alibct on 2017/3/5.
 */

public interface UserService {
    /**
     * 获取用户列表
     *
     * @return 所有用户信息
     */
    @GET("users")
    Call<List<User>> getAllUsers();

    /**
     * 根据手机号获取用户信息
     *
     * @param phone 手机号
     * @return 用户信息
     */
    @GET("users/phone/{phone}")
    Call<User> getUserByPhone(@Path("phone") String phone);

    /**
     * 根据用户名获取用户信息
     *
     * @param name 用户名
     * @return 用户信息
     */
    @GET("users/name/{name}")
    Call<User> getUserByName(@Path("name") String name);

    /**
     * 注册用户
     *
     * @param user 用户信息
     * @return 受影响行数
     */
    @POST("users")
    Call<String> signUp(@Body User user);
}
