package app.semiwarm.cn.service;

import java.util.List;

import app.semiwarm.cn.entity.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 用户服务类接口
 * Created by alibct on 2017/3/5.
 */

public interface UserService {
    /**
     * 获取用户列表
     * @return 所有用户信息
     */
    @GET("users")
    Call<List<User>> getAllUsers();

    /**
     * 根据手机号获取用户信息
     * @param phone 手机号
     * @return 用户信息
     */
    @GET("users/{phone}")
    Call<User> getUserByPhone(@Path("phone") String phone);
}
