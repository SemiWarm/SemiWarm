package app.semiwarm.cn.http;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 被观察者加载基础类
 * Created by alibct on 2017/4/20.
 */

public class ObservableLoader {
    /**
     * 用于初始化被观察者，提高代码的复用率
     * @param observable 服务接口观察者
     * @param <T> 范型
     * @return 被观察者
     */
    protected <T> Observable<T> observable(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io()) // 在io线程订阅
                .unsubscribeOn(Schedulers.io()) // 在io线程解除订阅
                .observeOn(AndroidSchedulers.mainThread()); // 在Android主线程消费事件
    }
}
