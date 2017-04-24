package app.semiwarm.cn.service.observable;

import app.semiwarm.cn.entity.User;
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.http.ObservableLoader;
import app.semiwarm.cn.http.RetrofitServiceManager;
import app.semiwarm.cn.service.UserService;
import rx.Observable;

/**
 * 用户服务类观察者类
 * Created by alibct on 2017/4/21.
 */

public class UserServiceObservable extends ObservableLoader {

    private UserService mUserService;

    public UserServiceObservable() {
        mUserService = RetrofitServiceManager.getInstance().create(UserService.class);
    }

    public Observable<BaseResponse<User>> getUserByAccount(String account) {
        return observable(mUserService.getUserByAccount(account));
    }

    public Observable<BaseResponse<User>> getUserByName(String name) {
        return observable(mUserService.getUserByName(name));
    }

    public Observable<BaseResponse<User>> signUp(User user) {
        return observable(mUserService.signUp(user));
    }
}
