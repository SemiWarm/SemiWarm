package app.semiwarm.cn.service;

import app.semiwarm.cn.entity.User;
import app.semiwarm.cn.http.BaseResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 用户服务类接口
 * Created by alibct on 2017/3/5.
 */

public interface UserService {

    /**
     * 根据账号获取用户信息
     *
     * @param account 账号
     * @return 用户信息
     */
    @GET("users/account/{account}")
    Observable<BaseResponse<User>> getUserByAccount(@Path("account") String account);

    /**
     * 根据用户名获取用户信息
     *
     * @param name 用户名
     * @return 用户信息
     */
    @GET("users/name/{name}")
    Observable<BaseResponse<User>> getUserByName(@Path("name") String name);

    /**
     * 注册用户
     *
     * @param user 用户信息
     * @return 受影响行数
     */
    @POST("users")
    Observable<BaseResponse<User>> signUp(@Body User user);
}
